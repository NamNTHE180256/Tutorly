document.addEventListener("DOMContentLoaded", function () {
    // Check if we are on the first page (register1.html)
    const registrationForm = document.getElementById("registration-form");
    if (registrationForm) {
        registrationForm.addEventListener("submit", function (event) {
            event.preventDefault(); // Prevent the default form submission
            const name = document.getElementById('full-name').value;
            const subjectName = document.getElementById('subject-name').value;
            const grade = document.getElementById('grade').value;

            // Save to localStorage
            localStorage.setItem('name', name);
            localStorage.setItem('subject', subjectName + ' ' + grade);

            // Redirect to the new page
            window.location.href = "register2.html"; // Redirect to register2.html
        });
    }

    // Check if we are on the second page (register2.html)
    const profileSetupForm = document.getElementById('profile-setup-form');
    if (profileSetupForm) {
        const name = localStorage.getItem('name');
        const subject = localStorage.getItem('subject');

        if (name && subject) {
            document.getElementById('display-name').textContent = name;
            document.getElementById('display-subject').textContent = subject;
        }

        document.getElementById('upload-image').addEventListener('change', function (event) {
            const imagePreview = document.getElementById('image-preview');
            imagePreview.innerHTML = '';

            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    imagePreview.style.backgroundImage = `url(${e.target.result})`;
                };
                reader.readAsDataURL(file);
            }
        });

        profileSetupForm.addEventListener('submit', function (event) {
            event.preventDefault(); // Prevent form from submitting the traditional way

            const imagePreview = document.getElementById('image-preview').style.backgroundImage;

            if (imagePreview) {
                // Simulate saving the profile data
                // You can add code here to save the data to a server or local storage if needed

                // Redirect to the next page
                window.location.href = 'register3.html'; // Change this to your actual next page URL
            } else {
                alert('Please complete all fields and upload a profile picture.');
            }
        });
        document.getElementById('back-button').addEventListener('click', function () {

            window.location.href = 'register1.html';
        });
    }

    // Check if we are on the third page (register3.html)
    const certificationSetupForm = document.getElementById('certification-setup-form');
    if (certificationSetupForm) {
        const subject = localStorage.getItem('subject');
        if (subject) {
            document.getElementById('subject').value = subject;
        }

        const noCertificationCheckbox = document.getElementById('no-certification');
        const certificationFields = document.getElementById('certification-fields');

        noCertificationCheckbox.addEventListener('change', function () {
            if (noCertificationCheckbox.checked) {
                certificationFields.classList.add('hidden');
                document.getElementById('save-continue').style.display = 'block';
            } else {
                certificationFields.classList.remove('hidden');
                document.getElementById('save-continue').style.display = 'block';
            }
        });

        document.getElementById('more-certification').addEventListener('click', function (event) {
            event.preventDefault();
            if (confirm("Do you want to enter another certification?")) {
                // Store current certification information
                storeCertification();

                // Clear form for new certification
                document.getElementById('upload-certification').value = "";
                document.getElementById('description').value = "";
                document.getElementById('issued-by').value = "";
            }
        });

        certificationSetupForm.addEventListener('submit', function (event) {
            event.preventDefault();
            storeCertification();
            // Redirect to the next page
            window.location.href = 'register4.html'; // Change to your actual next page URL
        });
        document.getElementById('back-button').addEventListener('click', function () {
            window.location.href = 'register2.html';
        });

        function storeCertification() {
            const certification = {
                subject: document.getElementById('subject').value,
                description: document.getElementById('description').value,
                issuedBy: document.getElementById('issued-by').value
            };
            console.log("Certification stored:", certification);
            // Store in localStorage or send to server
            // Example: localStorage.setItem('certification', JSON.stringify(certification));
        }
    }
    // Fourth page (register4.html)
    const educationSetupForm = document.getElementById('education-setup-form');
    if (educationSetupForm) {
        const startYearSelect = document.getElementById('start-year');
        const endYearSelect = document.getElementById('end-year');

        const currentYear = new Date().getFullYear();
        for (let year = currentYear; year >= 1900; year--) {
            let option = document.createElement('option');
            option.value = year;
            option.text = year;
            startYearSelect.add(option);

            option = document.createElement('option');
            option.value = year;
            option.text = year;
            endYearSelect.add(option);
        }

        document.getElementById('no-education').addEventListener('change', function (event) {
            const educationFields = document.getElementById('education-fields');
            const saveContinueButton = document.getElementById('save-continue');
            if (event.target.checked) {
                educationFields.classList.add('hidden');
                saveContinueButton.style.marginTop = '20px';
            } else {
                educationFields.classList.remove('hidden');
                saveContinueButton.style.marginTop = '0';
            }
        });

        educationSetupForm.addEventListener('submit', function (event) {
            event.preventDefault();
            // Add form submission logic
            window.location.href = 'register5.html';
        });
        document.getElementById('back-button').addEventListener('click', function () {
            window.location.href = 'register3.html';
        });

    }
    // Fifth page (register5.html)
    const introduceSetupForm = document.getElementById('introduce-setup-form');
    if (introduceSetupForm) {
        introduceSetupForm.addEventListener('submit', function (event) {
            event.preventDefault();
            const introduction = document.getElementById('introduction').value;

            // Save introduction to localStorage or handle submission as needed
            localStorage.setItem('introduction', introduction);

            // Redirect to the next page
            window.location.href = 'next_page.html'; // Change this to your actual next page URL
        });
    }
    document.getElementById('back-button').addEventListener('click', function () {
        window.location.href = 'register4.html';
    });
});