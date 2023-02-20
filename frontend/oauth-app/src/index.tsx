import React from 'react';
import {createRoot, Root} from 'react-dom/client';
import './index.css';
import AppRouter from "./route/AppRouter";
import reportWebVitals from './reportWebVitals';

const container: HTMLElement = document.getElementById('root')!!;
const root: Root = createRoot(container);
// @ts-ignore
root.render(<AppRouter tab={"home"}  />);
reportWebVitals();
