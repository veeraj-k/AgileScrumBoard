import './App.css'
import Navbar from "./components/main/Navbar.jsx";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import ScrumBoard from "./components/main/ScrumBoard.jsx";
import {Sprints} from "./components/main/Sprints.jsx";
import {ToastContainer} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import OpenTask from "./components/main/opentask/OpenTask.jsx";
import {createContext, useState} from "react";
import {Teams} from "./components/main/Teams.jsx";

export const RefreshContext = createContext();

function App() {
    const [refresh,setRefresh] = useState(0);
    return (
        <RefreshContext.Provider value={{refresh, setRefresh}}>
            <Router>
                <div>
                    <Navbar/>
                    <Routes>
                        {/*<Route path="/" element={<Boards/>}/>*/}
                        <Route path="/" element={<Teams/>}/>
                        <Route path="/teams/:teamid/sprint" element={<Sprints/>}/>
                        <Route path={`/teams/:teamid/sprint/:sprintid`} element={<ScrumBoard/>}/>
                        <Route path="/teams/:teamid/sprint/:sprintid/task/:taskid" element={<OpenTask/>}/>
                        {/*<Route path="/hello" element={<SelectCheck/>}/>*/}
                    </Routes>
                    <ToastContainer position="top-center" autoClose={1500}/>
                </div>
            </Router>
        </RefreshContext.Provider>
    )
}

export default App
