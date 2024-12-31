import { useState, useEffect } from "react";
import axios from "axios";
import React from "react";
import { useNavigate, Link } from "react-router-dom";

const ViewMentorCoursePurchases = () => {
  const [bookings, setBookings] = useState([]);
  const mentor_jwtToken = sessionStorage.getItem("mentor-jwtToken");
  const mentor = JSON.parse(sessionStorage.getItem("active-mentor"));

  let navigate = useNavigate();

  useEffect(() => {
    const getAllBookings = async () => {
      const bookingsRes = await retrieveAllBooking();
      if (bookingsRes) {
        setBookings(bookingsRes.bookings);
      }
    };

    getAllBookings();
  }, []);

  const retrieveAllBooking = async () => {
    const response = await axios.get(
      "http://localhost:8080/api/course/booking/fetch/mentor-wise?mentorId=" +
        mentor.id
    );
    console.log(response.data);
    return response.data;
  };

  const formatDateFromEpoch = (epochTime) => {
    const date = new Date(Number(epochTime));
    const formattedDate = date.toLocaleString(); // Adjust the format as needed

    return formattedDate;
  };

  return (
    <div className="mt-3">
      <div
        className="card form-card ms-2 me-2 mb-5 shadow-lg"
        style={{
          height: "45rem",
        }}
      >
        <div
          className="card-header custom-bg-text text-center bg-color"
          style={{
            borderRadius: "1em",
            height: "50px",
          }}
        >
          <h2>My Purchased Courses</h2>
        </div>
        <div
          className="card-body"
          style={{
            overflowY: "auto",
          }}
        >
          <div className="table-responsive">
            <table className="table table-hover text-color text-center">
              <thead className="table-bordered border-color bg-color custom-bg-text">
                <tr>
                  <th scope="col">Purchase Id</th>
                  <th scope="col">Thumnail</th>
                  <th scope="col">Course Name</th>
                  <th scope="col">Customer</th>
                  <th scope="col">Price</th>
                  <th scope="col">Purchase Time</th>
                  <th scope="col">Payment Id</th>
                </tr>
              </thead>
              <tbody>
                {bookings.map((booking) => {
                  return (
                    <tr>
                      <td>
                        <b>{booking.bookingId}</b>
                      </td>
                      <td>
                        <img
                          src={
                            "http://localhost:8080/api/course/" +
                            booking.course.thumbnail
                          }
                          class="img-fluid"
                          alt="thumbnail"
                          style={{
                            maxWidth: "90px",
                          }}
                        />
                      </td>
                      <td>
                        <b>{booking.course.name}</b>
                      </td>

                      <td>
                        <b>
                          {booking.customer.firstName +
                            " " +
                            booking.customer.lastName}
                        </b>
                      </td>
                      <td>
                        <b>&#8377;{booking.amount}</b>
                      </td>
                      <td>
                        <b>{formatDateFromEpoch(booking.bookingTime)}</b>
                      </td>
                      <td>
                        <b>{booking.payment.paymentId}</b>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewMentorCoursePurchases;