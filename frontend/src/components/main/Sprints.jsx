import React, {useContext, useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import {StartSprint} from "./StartSprint.jsx";
import {DeleteBoard} from "./DeleteBoard.jsx";
import {RefreshContext} from "../../App.jsx";
import {ActiveSprint} from "./ActiveSprint.jsx";
import {Backlog} from "./Backlog.jsx";


export const Sprints = () => {
    const [sprint,setSprint] = useState([]);
    const {refresh,setRefresh} = useContext(RefreshContext);
    const {teamid} = useParams();
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
        const getSprint = async () =>{
            const response = await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint`);
            const jsonData = await response.json();
            setSprint(jsonData);
            console.log("Sprint:",jsonData);
        };
        getSprint();
    }, [refresh]);



    return (
        <>
            <div className="pl-2.5">
                <div className="text-sm breadcrumbs">
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/">Teams</Link> </li>
                        <li><Link to="/">Sprints</Link> </li>
                    </ul>
                </div>
                <br/>

            </div>
            <ActiveSprint/>
            <Backlog/>
        </>
    )
}
