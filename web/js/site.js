function checkEmail(email) {
    // Define the regular expression for a valid email address with specific domains
    const regex = /^[^\s@]+@(gmail\.com|yahoo\.com|lookup\.com)$/i;

    // Test the email against the regular expression
    return regex.test(email);
}

function alertEmail() {
    const emailField = document.getElementsByName("email")[0]; // Get the input element with the name 'email'
    const email = emailField.value; // Get the value of the email field
    if (checkEmail(email)) {
        alert("Hợp lệ");
    } else {
        alert("Không hợp lệ");
    }
    return false; // Prevent form submission if part of a form
}

 document.addEventListener("DOMContentLoaded", function () {
            var togglePasswordBtns = document.querySelectorAll(".toggle-password");
            togglePasswordBtns.forEach(function (btn) {
                btn.addEventListener("click", function () {
                    var passwordField = btn.previousElementSibling;
                    var passwordFieldType = passwordField.getAttribute("type");
                    if (passwordFieldType === "password") {
                        passwordField.setAttribute("type", "text");
                        btn.innerHTML = '<i class="fa-solid fa-eye-slash"></i>';
                    } else {
                        passwordField.setAttribute("type", "password");
                        btn.innerHTML = '<i class="fa-solid fa-eye"></i>';
                    }
                });
            });
        });