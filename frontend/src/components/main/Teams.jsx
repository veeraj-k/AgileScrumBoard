import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
export const Teams = () => {
    const [teams, setTeams] = useState([]);
    const [isloading, setIsloading] = useState(true);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;

    useEffect(() => {
        const getBoards = async () =>{
            setIsloading(()=> true)
            const response = await fetch(`${backendApiUrl}/api/teams`);
            const jsonData = await response.json();
            setTeams(jsonData);
            setIsloading(()=> false)
            console.log("Teams:",jsonData)
        };
        getBoards();}, []);


    return (
        <>
            <div className="pl-2.5">
                <div className="text-sm breadcrumbs">
                    <ul>
                        <li><Link to="/">Projects</Link></li>
                        <li><Link to="/">Teams</Link></li>
                    </ul>
                </div>
            </div>
            {
                isloading ? <div className="skeleton w-full h-72"></div> :
                    <div className="overflow-x-auto">
                        <table className="table table-zebra">
                            {/* head */}
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>Team Name</th>
                                <th>Current Sprint</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                teams.map((team) => (
                                    <tr>
                                        <th>{team.id}</th>
                                        <td>{team.name}</td>
                                        <td>{team.sprint ? team.sprint.name : "No current sprint"}</td>
                                        <td>
                                            <Link to={`/teams/${team.id}/sprint`}>
                                                <button className="btn btn-primary btn-xs">View</button>
                                            </Link>
                                        </td>
                                    </tr>
                                ))
                            }
                            </tbody>
                        </table>
                    </div>
            }


        </>
    )
}
