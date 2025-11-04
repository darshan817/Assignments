import React from "react";

function TaskItem({ task, onEdit, onDelete }) {
  return (
    <li>
      {task.text}
      <button onClick={() => onEdit(task)}>Edit</button>
      <button onClick={() => onDelete(task.id)}>Delete</button>
    </li>
  );
}

export default TaskItem;
