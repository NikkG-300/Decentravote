const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const bcrypt = require('bcrypt');
const db = require('./db');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());

app.post('/api/login', async (req, res) => {
    try {
        const { username, password } = req.body;
        const [users] = await db.query('SELECT * FROM users WHERE username = ?', [username]);
        
        if (users.length === 0) {
            return res.status(401).json({ error: 'Invalid credentials' });
        }
        
        const user = users[0];
        const match = await bcrypt.compare(password, user.password);
        
        if (!match) {
            return res.status(401).json({ error: 'Invalid credentials' });
        }
        
        res.json({ id: user.id, username: user.username, role: user.role });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/register', async (req, res) => {
    try {
        const { username, password, role } = req.body;
        const hash = await bcrypt.hash(password, 10);
        const [result] = await db.query('INSERT INTO users (username, password, role) VALUES (?, ?, ?)', 
            [username, hash, role || 'voter']);
        res.json({ id: result.insertId, message: 'User registered' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.get('/api/elections', async (req, res) => {
    try {
        const [elections] = await db.query('SELECT * FROM elections ORDER BY created_at DESC');
        res.json(elections);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/elections', async (req, res) => {
    try {
        const { title, description, created_by } = req.body;
        const [result] = await db.query('INSERT INTO elections (title, description, created_by) VALUES (?, ?, ?)', 
            [title, description, created_by]);
        res.json({ id: result.insertId, message: 'Election created' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.get('/api/elections/:id/candidates', async (req, res) => {
    try {
        const [candidates] = await db.query('SELECT * FROM candidates WHERE election_id = ?', [req.params.id]);
        res.json(candidates);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/candidates', async (req, res) => {
    try {
        const { election_id, name, description } = req.body;
        const [result] = await db.query('INSERT INTO candidates (election_id, name, description) VALUES (?, ?, ?)', 
            [election_id, name, description]);
        res.json({ id: result.insertId, message: 'Candidate added' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.post('/api/vote', async (req, res) => {
    try {
        const { election_id, candidate_id, voter_id } = req.body;
        
        const [existing] = await db.query('SELECT * FROM votes WHERE election_id = ? AND voter_id = ?', 
            [election_id, voter_id]);
        
        if (existing.length > 0) {
            return res.status(400).json({ error: 'Already voted' });
        }
        
        const [result] = await db.query('INSERT INTO votes (election_id, candidate_id, voter_id) VALUES (?, ?, ?)', 
            [election_id, candidate_id, voter_id]);
        res.json({ message: 'Vote recorded' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.get('/api/elections/:id/results', async (req, res) => {
    try {
        const [results] = await db.query(`
            SELECT c.id, c.name, COUNT(v.id) as votes 
            FROM candidates c 
            LEFT JOIN votes v ON c.id = v.candidate_id 
            WHERE c.election_id = ? 
            GROUP BY c.id, c.name
        `, [req.params.id]);
        res.json(results);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.get('/api/check-vote/:election_id/:voter_id', async (req, res) => {
    try {
        const [votes] = await db.query('SELECT * FROM votes WHERE election_id = ? AND voter_id = ?', 
            [req.params.election_id, req.params.voter_id]);
        res.json({ hasVoted: votes.length > 0 });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});