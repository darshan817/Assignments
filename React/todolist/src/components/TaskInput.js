import React, { useState, useEffect } from "react";

function TaskInput({ addTask, updateTask, taskToEdit }) {
  const [text, setText] = useState("");

  // Populate input when editing
  useEffect(() => {
    if (taskToEdit) setText(taskToEdit.text);
  }, [taskToEdit]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!text.trim()) return;

    if (taskToEdit) {
      updateTask(taskToEdit.id, text);
    } else {
      addTask(text);
    }
    setText("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Enter a task..."
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <button type="submit">
        {taskToEdit ? "Update" : "Add"}
      </button>
    </form>
  );
}

export default TaskInput;
