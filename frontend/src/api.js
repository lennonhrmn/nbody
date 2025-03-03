import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/simulation';

export const configureSimulation = async (config) => {
    try {
        await axios.post(`${API_BASE_URL}/config`, config);
    } catch (error) {
        console.error('Error configuring simulation:', error);
        throw error;
    }
};
export const startSimulation = async () => {
    try {
        await axios.post(`${API_BASE_URL}/start`);
    } catch (error) {
        console.error('Error starting simulation:', error);
        throw error;
    }
};

export const stopSimulation = async () => {
    try {
        await axios.post(`${API_BASE_URL}/stop`);
    } catch (error) {
        console.error('Error stopping simulation:', error);
        throw error;
    }
};

export const resetSimulation = async () => {
    try {
        await axios.post(`${API_BASE_URL}/reset`);
    } catch (error) {
        console.error('Error resetting simulation:', error);
        throw error;
    }
};

export const addBody = async (body) => {
    try {
        await axios.post(`${API_BASE_URL}/body`, body);
    } catch (error) {
        console.error('Error adding body:', error);
        throw error;
    }
};

export const fetchBodies = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/state`);
        return response.data;
    } catch (error) {
        console.error('Error fetching bodies:', error);
        throw error;
    }
};
