const API_URL = "http://localhost:8080/simulation";

export async function fetchSimulationData() {
    try {
        const response = await fetch(API_URL);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error fetching data:", error);
        return [];
    }
}

