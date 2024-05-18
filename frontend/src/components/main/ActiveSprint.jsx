import React, {useContext, useEffect, useState} from "react";
import {RefreshContext} from "../../App.jsx";
import {Link, useParams} from "react-router-dom";
import {DeleteBoard} from "./DeleteBoard.jsx";
import {StartSprint} from "./StartSprint.jsx";
import {CompleteSprint} from "./CompleteSprint.jsx";

export const ActiveSprint = () => {
    const [sprint,setSprint] = useState([]);
    const {refresh,setRefresh} = useContext(RefreshContext);
    const {teamid} = useParams();
    const [isloading, setIsloading] = useState(true);

    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
        useEffect(() => {
        const getSprint = async () =>{
            setSprint([]);
            setIsloading(()=>true)
            const response = await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint`);
            if(response.ok){
                const jsonData = await response.json();
                setSprint(jsonData);

                console.log("Sprint:",jsonData);
            }
            setIsloading(()=>false)
            console.log(sprint.length)
        };
        getSprint();
    }, [refresh]);
    return (
        <>
            <div className="flex justify-between px-2 my-3">
                <span className="text-xl">Sprint</span>
                <StartSprint setRefresh={setRefresh}/>
            </div>
            {
                isloading ?
                    <div className="sprint-bg rounded-lg skeleton mx-2 my-1 p-2 h-32"></div>
                    :
                    <div className={"sprint-bg rounded-lg mx-2 my-1 p-2 min-h-32"}>
                        {sprint.id ?
                            <div className="overflow-x-auto sprint-bg">
                                <table className="table table-zebra">

                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th>Name</th>
                                        <th>Description</th>
                                        <th>Start date</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <tr>
                                        <th>{sprint.id}</th>
                                        <td><Link to={`/boards/${sprint.id}`}
                                                  className="hover:text-primary underline">{sprint.name}</Link></td>
                                        <td
                                            dangerouslySetInnerHTML={{
                                                __html: sprint.description,
                                            }}
                                        />
                                        <td>{sprint.startdate}</td>
                                        <td><Link to={`/teams/${teamid}/sprint/${sprint.id}`}>
                                            <button className="btn btn-primary btn-xs">View Sprint</button>
                                        </Link></td>
                                        <td>
                                            <div>
                                                <CompleteSprint/>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            :
                            <div className="flex justify-center items-center">
                                <div className="text-center">
                                    No Active Sprint!
                                </div>
                            </div>

                        }
                    </div>
            }


        </>
    )
}
