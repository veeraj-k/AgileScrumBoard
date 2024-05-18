import React, {useContext, useEffect, useState} from 'react';
import ReactDOM from 'react-dom';
import { IoIosWarning } from "react-icons/io";
import { FaArrowRightLong } from "react-icons/fa6";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";
import axios from "axios";
import {RefreshContext} from "../../App.jsx";
import { FaTrashAlt } from "react-icons/fa";
export const DeleteColumn = (prop) => {
    const [showModal, setShowModal] = useState(false);

    const {teamid} =useParams();
    const[columns,setColumns] = useState([]);
    const [targetColumn, setTargetColumn] = useState("");
    const {refresh,setRefresh} = useContext(RefreshContext);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
        const getColumns = async () => {
            const response = await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/plaincolumns`);
            const jsonData = await response.json();
            setColumns(jsonData);

        }
        getColumns()
    }, []);

    const handleColumnChange = (e) => {
        setTargetColumn(e.target.value);
        console.log(e.target.value)
    }
    const handleSubmitColumnChange = async (e) => {
        try{
            if(targetColumn!=""){
                const response = await axios.delete(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/${prop.column.id}?targetid=${targetColumn}`)
                if(response.status == 200){
                    toast.success(response.data);
                    setRefresh(prev => prev+1);
                    setShowModal(false);
                }
                else {
                    throw Error(response.statusText);
                }
            }
            else {
                throw Error("Select the status:")
            }
        }catch (error){
            toast.error("Column with done status cannot be removed!");
        }
    }
    const handleClose = () => setShowModal(false);

    const modal = showModal ? (
        <div className="modal modal-open">
            <div className="modal-box">
                {/* Modal content */}


                <div className={"flex gap-2 p-3"}>
                    <div className={" py-2"}>
                        <IoIosWarning style={{color:"red"}}/>
                    </div>

                    <div className={"text-lg  font-bold"}>

                        Move work from {prop.column.title} column
                    </div>
                </div>

                {/*Content*/}
                <div>
                    <div>Select a new home for any work with the {prop.column.title}.</div>
                    <br/>
                    <div>The status you send this work to will become the landing place for all newly created issues.</div>
                </div>

                {/*List */}
                <div className={"flex gap-1 py-2"}>
                    <div className={"basis-1/2"}>
                        <div className={"text-sm"}>This status will be Deleted:</div>
                        <div className={"flex justify-between py-3" }>
                            <div>
                                <strike>{prop.column.title}</strike>
                            </div>
                            <div >
                                <FaArrowRightLong/>
                            </div>

                        </div>

                    </div>
                    <div className={"basis-1/2"}>
                        <div className={"text-sm"}>Move Existing tasks to :</div>

                        <select className="select select-error w-full max-w-xs" onChange={handleColumnChange}>
                            <option value={""}>Select the status:</option>
                            {
                                columns.map((col) => (col.id!=prop.column.id && <option key={col.id} value={col.id}>{col.title}</option>))
                            }
                        </select>
                    </div>
                </div>

                <div className={"flex justify-end gap-1"}>
                    <button className="btn btn-ghost" onClick={handleClose}>
                        Close
                    </button>
                    <button className="btn btn-error" onClick={handleSubmitColumnChange}>
                        Delete
                    </button>

                </div>

            </div>
        </div>
    ) : null;

    return (
        <>
            <FaTrashAlt style={{color:"red"}} onClick={() => setShowModal(true)} />
            {ReactDOM.createPortal(modal, document.body)}
            {ReactDOM.createPortal(modal, document.body)}
        </>
    );
};