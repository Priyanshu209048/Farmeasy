document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.querySelector("tbody");

    // Populate table dynamically from CUSTOMER_APPOINTMENTS
    function loadAppointments() {
        tableBody.innerHTML = ""; // Clear existing rows

        CUSTOMER_APPOINTMENTS.forEach(appointment => {
            const row = document.createElement("tr");
            row.dataset.appointmentId = appointment.appointmentId;

            row.innerHTML = `
                <td>${appointment.name}</td>
                <td class="status ${appointment.status.toLowerCase()}">${appointment.status}</td>
                <td>
                    <button class="schedule">Schedule</button>
                    <button class="cancel">Cancel</button>
                </td>
            `;

            tableBody.appendChild(row);
        });

        addEventListeners(); // Attach event listeners after adding rows
    }

    // Function to update the status dynamically
    function updateAppointmentStatus(appointmentId, newStatus) {
        const appointment = CUSTOMER_APPOINTMENTS.find(appt => appt.appointmentId === appointmentId);
        if (appointment) {
            appointment.status = newStatus;
        }
        loadAppointments(); // Refresh the table with updated data
    }

    // Attach event listeners to buttons
    function addEventListeners() {
        document.querySelectorAll(".schedule").forEach(button => {
            button.addEventListener("click", function () {
                let row = this.closest("tr");
                let appointmentId = row.dataset.appointmentId;
                updateAppointmentStatus(appointmentId, "Scheduled");
            });
        });

        document.querySelectorAll(".cancel").forEach(button => {
            button.addEventListener("click", function () {
                let row = this.closest("tr");
                let appointmentId = row.dataset.appointmentId;
                updateAppointmentStatus(appointmentId, "Cancelled");
            });
        });
    }

    loadAppointments(); // Load appointments on page load
});

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".schedule").forEach(button => {
        button.addEventListener("click", function () {
            let row = this.closest("tr");
            let statusCell = row.querySelector(".status");
            let appointmentId = row.dataset.appointmentId;

            // Update status in data
            updateAppointmentStatus(appointmentId, "Scheduled");

            // Update UI
            statusCell.textContent = "Scheduled";
            statusCell.className = "status scheduled";
        });
    });

    document.querySelectorAll(".cancel").forEach(button => {
        button.addEventListener("click", function () {
            let row = this.closest("tr");
            let statusCell = row.querySelector(".status");
            let appointmentId = row.dataset.appointmentId;

            // Update status in data
            updateAppointmentStatus(appointmentId, "Cancelled");

            // Update UI
            statusCell.textContent = "Cancelled";
            statusCell.className = "status cancelled";
        });
    });
});
