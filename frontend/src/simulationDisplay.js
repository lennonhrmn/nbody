// SimulationDisplay.js
import React from "react";
import useSimulationData from "./useSimulationData";

const SimulationDisplay = () => {
    const data = useSimulationData();

    console.log("Data:", data);

    return (
        <div>
            <h1>N-Body Simulation</h1>
            <div className="simulation-canvas" style={{ position: 'relative', width: '100%', height: '500px', border: '1px solid #ccc' }}>
                {data.map((body, index) => (
                    <div
                        key={index}
                        style={{
                            position: 'absolute',
                            left: `${body.x * 100 + 250}px`,
                            top: `${body.y * 100 + 250}px`,
                            width: '20px',
                            height: '20px',
                            backgroundColor: body.color,
                            borderRadius: '50%',
                            transform: 'translate(-50%, -50%)',
                        }}
                    />
                ))}
            </div>
            <ul>
                {data.map((body, index) => (
                    <li key={index}>
                        <strong>{body.name}</strong>: x={body.x.toFixed(2)}, y={body.y.toFixed(2)}, vx={body.vx.toFixed(2)}, vy={body.vy.toFixed(2)}, mass={body.mass.toExponential(2)}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default SimulationDisplay;
