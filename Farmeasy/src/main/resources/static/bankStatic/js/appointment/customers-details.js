const CUSTOMER_APPOINTMENTS = [
    {
        name: "Arav",
        appointmentId: "A001",
        status: "Scheduled",
    },
    {
        name: "Kumar",
        appointmentId: "A002",
        status: "Pending",
    },
    {
        name: "Abhishek",
        appointmentId: "A003",
        status: "Pending",
    },
    {
        name: "Mishra",
        appointmentId: "A004",
        status: "Pending",
    }
];

// Make the function globally accessible
window.updateAppointmentStatus = function(appointmentId, newStatus) {
    const appointment = CUSTOMER_APPOINTMENTS.find(appt => appt.appointmentId === appointmentId);
    if (appointment) {
        appointment.status = newStatus;
        console.log(`Updated: ${appointmentId} -> ${newStatus}`); // Debugging log
    }
};
