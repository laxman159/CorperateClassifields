import axios from "axios";
import React from "react";
import Helmet from "react-helmet";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { offerBaseURL } from "../utils/UrlRoutes";

const AddOffer = () => {
  const navigate = useNavigate();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) => {
    const userObject = window.localStorage.getItem("employee");
    const token = JSON.parse(userObject).authToken;
    axios
      .post(`${offerBaseURL}/addOffer`, data, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then(function (response) {
        console.log(response.data);
        if (response.data.status === "ACCEPTED") {
          navigate("/");
        }
      })
      .catch(function (error) {
        console.log(error.response.status);
        if (error.response.status === 500 || error.response.status === 401) {
          navigate("/login");
        }
      });
  };
  return (
    <div className='w-2/2 p-10 mx-10 rounded mt-10 flex flex-col items-center justify-center  '>
      <Helmet>
        <title>Add Offer | Corperate Classifields</title>
      </Helmet>
      <h1 className='mb-5 text-green-500 text-3xl font-thin'>Add Offer</h1>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className='flex sm:w-full md:w-1/2 flex-col items-center justify-center bg-gray-200 shadow-xl p-10 md:p-30 rounded'
      >
        <div className='relative z-0 sm:w-3/4  mb-10 group rounded  mt-10 '>
          <input
            type='text'
            name='name'
            className='block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-black dark:border-gray-600 dark:focus:border-green-500 focus:outline-none focus:ring-0  focus:border-green-500 peer'
            placeholder=' '
            required
            {...register("name", { required: true })}
          />
          <label
            for='name'
            className='peer-focus:font-medium absolute text-sm  text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-green-600 peer-focus:dark:text-green-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6'
          >
            Item name
          </label>
          {errors.name && (
            <span className='text-red-600'>Item name is required</span>
          )}
        </div>
        <div className='relative z-0 sm:w-3/4 mb-10 group'>
          <textarea
            name='description'
            id='floating_description'
            className='block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-black dark:border-gray-600 dark:focus:border-green-500 focus:outline-none focus:ring-0 focus:border-green-600 peer'
            placeholder=' '
            required
            {...register("description", { required: true })}
          />
          <label
            for='description'
            className='peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-green-600 peer-focus:dark:text-green-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6'
          >
            Description
          </label>
          {errors.description && (
            <span className='text-red-600'>Description is required</span>
          )}
        </div>
        <div className='relative z-0 sm:w-3/4 mb-10 group'>
          <input
            type='text'
            name='category'
            id='floating_repeat_password'
            className='block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-black dark:border-gray-600 dark:focus:border-green-500 focus:outline-none focus:ring-0 focus:border-green-600 peer'
            placeholder=' '
            required
            {...register("category", { required: true })}
          />
          <label
            for='cateogry'
            className='peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:left-0 peer-focus:text-green-600 peer-focus:dark:text-green-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6'
          >
            Category
          </label>
          {errors.category && (
            <span className='text-red-600'>Category is required</span>
          )}
        </div>

        <button
          type='submit'
          className='text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800'
        >
          Submit
        </button>
      </form>
    </div>
  );
};

export default AddOffer;
