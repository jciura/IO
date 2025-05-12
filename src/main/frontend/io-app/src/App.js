import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import LoginForm from "./LoginForm";
import Layout from "./Layout";
import ClassesView from "./ClassesView";

function App() {
  return (
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<Layout  />}>
                  <Route index path="/login" element={<LoginForm />} />
                  <Route path="/classes/:user_id" element={<ClassesView />} />
              </Route>
          </Routes>
      </BrowserRouter>
  );
}

export default App;
