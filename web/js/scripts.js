/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let selectedRating = 0;

document.querySelectorAll('.star').forEach(star => {
    star.addEventListener('mouseover', function() {
        const value = this.getAttribute('data-value');
        resetStars();
        highlightStars(value);
    });

    star.addEventListener('mouseout', function() {
        resetStars();
        highlightSelectedStars();
    });

    star.addEventListener('click', function() {
        const value = this.getAttribute('data-value');
        selectedRating = value; // Save the selected rating
        resetStars();
        document.querySelectorAll('.star').forEach((s, index) => {
            if (index < value) {
                s.classList.add('selected');
            }
        });
        console.log("Selected rating:", value);
    });
});

function highlightStars(value) {
    document.querySelectorAll('.star').forEach((star, index) => {
        if (index < value) star.classList.add('hovered');
    });
}

function resetStars() {
    document.querySelectorAll('.star').forEach(star => star.classList.remove('hovered'));
}

function highlightSelectedStars() {
    document.querySelectorAll('.star.selected').forEach(star => star.classList.add('hovered'));
}

