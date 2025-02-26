import React, { useState, useEffect, useRef, useCallback } from 'react';
import { configureSimulation, startSimulation, stopSimulation, resetSimulation, addBody, fetchBodies } from '../api';
import './NBodySimulation.css';

const NBodySimulation = () => {
  const [bodies, setBodies] = useState([]);
  const [isRunning, setIsRunning] = useState(false);
  const [config, setConfig] = useState({
    gravitationalConstant: 0.1,
    timeStep: 0.01,
    dimensions: 2
  });
  const [newBody, setNewBody] = useState({
    mass: 1.0,
    position: [0, 0],
    velocity: [0, 0],
    color: '#' + Math.floor(Math.random() * 16777215).toString(16),
    radius: 10
  });

  const canvasRef = useRef(null);
  const animationRef = useRef(null);

  // Canvas dimensions
  const width = 800;
  const height = 600;

  // Center point of the canvas
  const centerX = width / 2;
  const centerY = height / 2;

  // Scale factor for visualization
  const scale = 100;

  useEffect(() => {
    // Initial configuration of the simulation
    configureSimulation(config);

    // Cleanup animation frame on unmount
    return () => {
      if (animationRef.current) {
        cancelAnimationFrame(animationRef.current);
      }
    };
  }, [config]);

  useEffect(() => {
    if (isRunning) {
      startSimulation();
      startRendering();
    } else {
      stopSimulation();
      if (animationRef.current) {
        cancelAnimationFrame(animationRef.current);
      }
    }
  }, [isRunning]);

  const startRendering = useCallback(() => {
    const render = async () => {
      const updatedBodies = await fetchBodies();
      setBodies(updatedBodies);
      renderCanvas();
      animationRef.current = requestAnimationFrame(render);
    };
    render();
  }, []);

  const renderCanvas = () => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');

    // Clear canvas
    ctx.clearRect(0, 0, width, height);

    // Draw each body
    bodies.forEach(body => {
      const x = centerX + body.position[0] * scale;
      const y = centerY + body.position[1] * scale;

      ctx.beginPath();
      ctx.arc(x, y, Math.sqrt(body.mass) * body.radius, 0, 2 * Math.PI);
      ctx.fillStyle = body.color;
      ctx.fill();

      // Draw velocity vector
      ctx.beginPath();
      ctx.moveTo(x, y);
      ctx.lineTo(x + body.velocity[0] * 10, y + body.velocity[1] * 10);
      ctx.strokeStyle = 'rgba(255, 255, 255, 0.5)';
      ctx.stroke();
    });
  };

  const handleConfigChange = (e) => {
    const { name, value } = e.target;
    setConfig({
      ...config,
      [name]: parseFloat(value)
    });
  };

  const handleNewBodyChange = (e) => {
    const { name, value } = e.target;

    if (name === 'positionX') {
      setNewBody({
        ...newBody,
        position: [parseFloat(value), newBody.position[1]]
      });
    } else if (name === 'positionY') {
      setNewBody({
        ...newBody,
        position: [newBody.position[0], parseFloat(value)]
      });
    } else if (name === 'velocityX') {
      setNewBody({
        ...newBody,
        velocity: [parseFloat(value), newBody.velocity[1]]
      });
    } else if (name === 'velocityY') {
      setNewBody({
        ...newBody,
        velocity: [newBody.velocity[0], parseFloat(value)]
      });
    } else if (name === 'color') {
      setNewBody({
        ...newBody,
        color: value
      });
    } else {
      setNewBody({
        ...newBody,
        [name]: parseFloat(value)
      });
    }
  };

  const applyConfig = useCallback(() => {
    configureSimulation(config);
  }, [config]);

  const handleAddBody = async () => {
    await addBody(newBody);
    setNewBody({
      mass: 1.0,
      position: [0, 0],
      velocity: [0, 0],
      color: '#' + Math.floor(Math.random() * 16777215).toString(16),
      radius: 10
    });
  };

  return (
    <div className="n-body-simulation">
      <h1>N-Body Simulation</h1>

      <div className="simulation-container">
        <canvas
          ref={canvasRef}
          width={width}
          height={height}
          style={{ border: '1px solid #333', background: '#000' }}
        />
      </div>

      <div className="controls">
        <div className="simulation-controls">
          <h2>Simulation Controls</h2>
          <button onClick={() => setIsRunning(!isRunning)}>
            {isRunning ? 'Stop' : 'Start'}
          </button>
          <button onClick={resetSimulation}>Reset</button>
        </div>

        <div className="config-form">
          <h2>Simulation Config</h2>
          <div>
            <label>
              Gravitational Constant:
              <input
                type="number"
                name="gravitationalConstant"
                step="0.01"
                value={config.gravitationalConstant}
                onChange={handleConfigChange}
              />
            </label>
          </div>
          <div>
            <label>
              Time Step:
              <input
                type="number"
                name="timeStep"
                step="0.001"
                value={config.timeStep}
                onChange={handleConfigChange}
              />
            </label>
          </div>
          <div>
            <label>
              Dimensions (2 or 3):
              <input
                type="number"
                name="dimensions"
                min="2"
                max="3"
                value={config.dimensions}
                onChange={handleConfigChange}
              />
            </label>
          </div>
          <button onClick={applyConfig}>Apply Config</button>
        </div>

        <div className="add-body-form">
          <h2>Add New Body</h2>
          <div>
            <label>
              Mass:
              <input
                type="number"
                name="mass"
                step="0.1"
                value={newBody.mass}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <div>
            <label>
              Position X:
              <input
                type="number"
                name="positionX"
                step="0.1"
                value={newBody.position[0]}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <div>
            <label>
              Position Y:
              <input
                type="number"
                name="positionY"
                step="0.1"
                value={newBody.position[1]}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <div>
            <label>
              Velocity X:
              <input
                type="number"
                name="velocityX"
                step="0.1"
                value={newBody.velocity[0]}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <div>
            <label>
              Velocity Y:
              <input
                type="number"
                name="velocityY"
                step="0.1"
                value={newBody.velocity[1]}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <div>
            <label>
              Radius:
              <input
                type="number"
                name="radius"
                step="1"
                value={newBody.radius}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <div>
            <label>
              Color:
              <input
                type="color"
                name="color"
                value={newBody.color}
                onChange={handleNewBodyChange}
              />
            </label>
          </div>
          <button onClick={handleAddBody}>Add Body</button>
        </div>
      </div>

      <div className="bodies-list">
        <h2>Bodies ({bodies.length})</h2>
        <ul>
          {bodies.map((body, index) => (
            <li key={body.id || index}>
              Mass: {body.mass.toFixed(2)},
              Position: [{body.position[0].toFixed(2)}, {body.position[1].toFixed(2)}],
              Velocity: [{body.velocity[0].toFixed(2)}, {body.velocity[1].toFixed(2)}]
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default NBodySimulation;
