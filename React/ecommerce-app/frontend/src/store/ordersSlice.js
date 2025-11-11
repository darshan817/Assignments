// src/store/ordersSlice.js
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  orders: [] // store all completed orders
};

const ordersSlice = createSlice({
  name: 'orders',
  initialState,
  reducers: {
    addOrder: (state, action) => {
      state.orders.push(action.payload);
    }
  }
});

export const { addOrder } = ordersSlice.actions;
export default ordersSlice.reducer;
