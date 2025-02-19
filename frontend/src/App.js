import React, { useEffect, useState } from "react";
import { fetchSimulationData } from "./api";

function App() {
  const [data, setData] = useState([]);

  console.log("Data:", data);

  useEffect(() => {
    fetchSimulationData().then((res) => {
      console.log("Data received:", res);
      setData(res);
    });
  }, []);

  return (
    <div>
      <h1>N-Body Simulation </h1>
      <ul>
        {data.map((item) => (
          <li key={item.id}>
            {item.name} - {item.mass}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;