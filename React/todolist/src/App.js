import React, { useState, useEffect } from "react";
import TaskInput from "./components/TaskInput";
import TaskList from "./components/TaskList";
import './App.css';


function App() {
  const [tasks, setTasks] = useState(
    JSON.parse(localStorage.getItem("tasks")) || []
  );
  const [taskToEdit, setTaskToEdit] = useState(null);

  // Load tasks from localStorage on first render
  useEffect(() => {
    const saved = JSON.parse(localStorage.getItem("tasks")) || [];
    setTasks(saved);
  }, []);

  // Save tasks to localStorage whenever they change
  useEffect(() => {
    localStorage.setItem("tasks", JSON.stringify(tasks));
  }, [tasks]);

  // Add new task
  const addTask = (text) => {
    const newTask = { id: Date.now().toString(), text };
    setTasks([...tasks, newTask]);
  };

  // Update (edit) task
  const updateTask = (id, newText) => {
    setTasks(tasks.map(t => (t.id === id ? { ...t, text: newText } : t)));
    setTaskToEdit(null);
  };

  // Delete task
  const deleteTask = (id) => {
    setTasks(tasks.filter(t => t.id !== id));
  };

  return (
    <div className="App">
      <h1>React To-Do List</h1>

      <TaskInput
        addTask={addTask}
        updateTask={updateTask}
        taskToEdit={taskToEdit}
      />

      <TaskList
        tasks={tasks}
        onEdit={setTaskToEdit}
        onDelete={deleteTask}
      />
    </div>
  );
}

export default App;
