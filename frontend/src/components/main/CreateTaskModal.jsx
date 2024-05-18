import React, {useContext, useEffect, useState} from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import {RefreshContext} from "../../App.jsx";
import axios from "axios";
import {toast} from "react-toastify";
import {useParams} from "react-router-dom";

const toolbarOptions = [
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    ['blockquote', 'code-block'],
    ['link', 'formula'],
    [{'font': []}],
    [{'list': 'ordered'}, {'list': 'bullet'}, {'list': 'check'}],
    [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript

    [{'align': []}],

];

const module = {
    toolbar: toolbarOptions,
}
const CreateTaskModal = () => {
    const [isOpen, setIsOpen] = useState(false);
    const {refresh, setRefresh} = useContext(RefreshContext);

    const {sprintid} = useParams();
    const {teamid} = useParams();
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    const openModal = () => {
        console.log("Sprint:",sprintid)
        console.log("Team:",teamid)
        setIsOpen(true);
    };

    const closeModal = () => {
        setIsOpen(false);
    };


    const [sprint, setSprint] = useState([]);
    const [users, setUsers] = useState([]);
    const [formData, setFormData] = useState(
        {
            type: '',
            columnid: '',
            storypoints: '',
            title: '',
            description: '',
            userid: "",

        }
    );
    const [columns, setColumns] = useState();



    useEffect(
        () => {
            const fetchSprint = async () => {
                console.log(sprintid);
                const response = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint`));
                const jsonData = await response.json();

                const userResponse = await fetch(`${backendApiUrl}/api/users`);
                const userjsonData = await userResponse.json();

                setUsers(userjsonData);
                setSprint(jsonData);
            };
            const fetchColumns = async () => {
                const response = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns`));
                const jsonData = await response.json();
                setColumns(jsonData);
                console.log("Columns:", jsonData);
                console.log(columns);
            };
            fetchColumns();
            fetchSprint();
        }, [refresh]
    );


    const handleChange = (e) => {
        if (e.target.id == "boardid") {
            fetchColumns(e.target.value);
            const newData = {...formData}
            newData.boardid = e.target.value;
            newData.columnid = "";
            setFormData(newData)
        } else if (e.target.id == "columnid") {
            console.log("column edit")
            setFormData({
                ...formData,
                columnid: e.target.value
            })
        } else {
            const newData = {...formData};
            newData[e.target.id] = e.target.value;
            setFormData(newData);
        }

    };
    const handlesubmit = async () => {
        console.log(formData)
        try {
            if (formData.type == "") {
                throw Error("Select type!!");
            } else if (formData.columnid == "") {
                throw Error("Select status!!");
            } else if (formData.storypoints == "") {
                throw Error("Select Story points!!");
            } else if (formData.title == "") {
                throw Error("Title cannot be empty!");
            }
            const response = await axios.post(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/${formData.columnid}/tasks/user/${formData.userid}`,
                {
                    title: formData.title,
                    description: formData.description,
                    storyPoints: formData.storypoints,
                    type: formData.type,

                }
            )
            if(response.status == 201){
                toast.success("Task Created sucesfully!")
                setRefresh(prev => prev+1)
            }else {
                throw Error("Error -" +response.status);
            }
            setFormData({
                type: '',
                columnid: '',
                storypoints: '',
                title: '',
                description: '',
                userid: "",
            })
            closeModal()
        } catch (error) {
            toast.error(error.message);
        }
    }
    return (
        <>
            <button
                className="btn btn-primary"
                onClick={openModal}
            >
                Create
            </button>

            {isOpen && (
                <div className="modal modal-open">
                    <div className="modal-box">
                        <h3 className="font-bold text-lg">Create Issue</h3>
                        <div className="py-4">
                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Sprint <span className="text-red-600">*</span></span>
                                </label>
                                <input type="text" placeholder={sprint.name}
                                       className="input input-bordered w-full max-w-xs" disabled/>
                            </div>

                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Task Type *</span>
                                </label>
                                <select className="select select-bordered" onChange={handleChange} id={"type"}
                                        value={formData.type}>
                                    <option disabled={formData.type !== ''} value={"Select Type"}>Select Status
                                    </option>
                                    <option>Story</option>
                                    <option>Bug</option>
                                    <option>Improvement</option>
                                    <option>Spike</option>
                                    <option>Incident</option>
                                </select>
                            </div>

                            <div className="form-control">
                            <label className="label">
                                    <span className="label-text">Status</span>
                                </label>

                                <select className="select select-bordered" id={"columnid"} value={formData.columnid}
                                        onChange={handleChange}>
                                    <option disabled={formData.columnid !== ''} value={"Select Status"}>Select Status
                                    </option>
                                    {Array.isArray(columns) && columns.map((col) => (
                                        <option value={col.id}>{col.title}</option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Assign To </span>
                                </label>

                                <select className="select select-bordered" id={"userid"} value={formData.userid}
                                        onChange={handleChange}>
                                    <option disabled={formData.userid !== ''} value={"Select Type"}>Select User
                                    </option>
                                    {Array.isArray(users) && users.map((user) => (
                                        <option value={user.id}>{user.name}</option>
                                    ))}
                                </select>
                            </div>
                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Story Points</span>
                                </label>
                                <input
                                    type="number"
                                    className="input input-bordered"
                                    id={"storypoints"}
                                    onChange={handleChange}
                                    value={formData.storypoints}
                                />
                            </div>

                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Title *</span>
                                </label>
                                <input
                                    type="text"
                                    placeholder="Summary is required"
                                    className="input input-bordered"
                                    id="title"
                                    onChange={handleChange}
                                    value={formData.title}
                                />
                            </div>

                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Description</span>
                                </label>
                                <ReactQuill modules={module} theme="snow" value={formData.description}
                                            onChange={(d) => setFormData({
                                                ...formData,
                                                description: d
                                            })}/>
                            </div>
                        </div>

                        <div className="modal-action">
                            <button className="btn btn-primary" onClick={handlesubmit}>
                                Create
                            </button>
                            <button className="btn btn-ghost" onClick={closeModal}>
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};

export default CreateTaskModal;