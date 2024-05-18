import ReactQuill from "react-quill";
import React, {useContext, useState} from "react";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import {toast} from "react-toastify";
import axios from "axios";
import {RefreshContext} from "../../App.jsx";
import {useParams} from "react-router-dom"; // Import the CSS file

export const StartSprint = () => {
    const [isOpen, setIsOpen] = useState(false);
    const {refresh,setRefresh} = useContext(RefreshContext);
    const {teamid} = useParams();
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        startdate: "",
        enddate: "",
    });
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    const openModal = () => {
        setIsOpen(true);
    };

    const closeModal = () => {
        setIsOpen(false);
    };

    const handleOnChange = (e) => {

        const newData = {
            ...formData, [e.target.id]: e.target.value
        }
        setFormData({...formData, [e.target.id]: e.target.value});
        console.log(newData);
    };

    const handleSubmit = async (e) => {
        try {
            if (formData.name.length < 1) {
                throw new Error("Name cannot be empty!");
            }
            else if (formData.startdate.length < 10) {
                throw new Error("Start date cannot be empty");
            }

            const response = await axios.post(`${backendApiUrl}/api/teams/${teamid}/sprint/start`, formData);
            if (response.status == 201) {
                toast.success("Board added successfully!", {});

                setFormData({
                    name: "",
                    description: "",
                    startdate: "",
                    enddate: "",
                });
                setIsOpen(false);
                setRefresh((prev) => (prev + 1));
            } else {
                toast.error("Error - " + response.status, {});
            }

        } catch (error) {
            toast.error(error.message, {});
            console.error(error.message);
        }

    };
    return (
        <>
            <button
                className="btn btn-primary"
                onClick={openModal}
            >
                Start New Sprint
            </button>

            {isOpen && (
                <div className="modal modal-open">
                    <div className="modal-box">
                        <h3 className="font-bold text-lg">Start New Sprint</h3>
                        <div className="py-4">
                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Sprint name<span
                                        className="text-red-600">*</span></span>
                                </label>

                                <input
                                    type="text"
                                    placeholder="Sprint Name"
                                    className="input input-bordered"
                                    id="name"
                                    value={formData.name}
                                    onChange={handleOnChange}
                                />

                            </div>

                            <div className="form-control">

                                <label className="label">
                                    <span className="label-text">Start Date<span
                                        className="text-red-600">*</span> </span>
                                </label>

                                <DatePicker
                                    selected={formData.startdate}
                                    onChange={(startdate) => setFormData({
                                        ...formData,
                                        startdate: startdate.toISOString().slice(0, 10)
                                    })}
                                    minDate={new Date()} // Disable dates before today
                                    dateFormat="yyyy-MM-dd" // Customize the date format if needed
                                    placeholderText="Select a date"
                                    className="input input-bordered"
                                />

                            </div>
                            <div className="form-control">

                                <label className="label">
                                    <span className="label-text">End Date<span
                                        className="text-red-600">*</span> </span>
                                </label>

                                <DatePicker
                                    selected={formData.enddate}
                                    onChange={(enddate) => setFormData({
                                        ...formData,
                                        enddate: enddate.toISOString().slice(0, 10)
                                    })}
                                    minDate={new Date()} // Disable dates before today
                                    dateFormat="yyyy-MM-dd" // Customize the date format if needed
                                    placeholderText="Select a date"
                                    className="input input-bordered"
                                />

                            </div>


                            <div className="form-control">
                                <label className="label">
                                    <span className="label-text">Sprint Goal </span>
                                </label>
                                <ReactQuill theme="snow" value={formData.description}
                                            onChange={(description) => setFormData({
                                                ...formData,
                                                description: description
                                            })}/>
                            </div>
                        </div>

                        <div className="modal-action">
                            <button className="btn btn-primary" onClick={handleSubmit}>
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
}
