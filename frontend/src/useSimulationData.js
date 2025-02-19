// useSimulationData.js
import { useEffect, useState } from "react";
import { fetchSimulationData } from "./api";

const useSimulationData = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetchSimulationData().then((res) => {
            console.log("Data received:", res);
            setData(res);
        });
    }, []);

    return data;
};

export default useSimulationData;
