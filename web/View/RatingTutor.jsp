<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Rate Tutor</title>
        <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

            :root {
                --yellow: #FFBD13;
                --blue: #4383FF;
                --blue-d-1: #3278FF;
                --light: #F5F5F5;
                --grey: #AAA;
                --white: #FFF;
                --shadow: 8px 8px 30px rgba(0,0,0,.05);
            }

            body {
                background: var(--light);
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                padding: 1rem;
            }






            .wrapper {
                background: var(--white);
                padding: 2rem;
                max-width: 576px;
                width: 100%;
                border-radius: .75rem;
                box-shadow: var(--shadow);
                text-align: center;
            }
            .wrapper h3 {
                font-size: 1.5rem;
                font-weight: 600;
                margin-bottom: 1rem;
            }
            .rating {
                display: flex;
                justify-content: center;
                align-items: center;
                grid-gap: .5rem;
                font-size: 2rem;
                color: var(--yellow);
                margin-bottom: 2rem;
            }
            .rating .star {
                cursor: pointer;
            }
            .rating .star.active {
                opacity: 0;
                animation: animate .5s calc(var(--i) * .1s) ease-in-out forwards;
            }

            @keyframes animate {
                0% {
                    opacity: 0;
                    transform: scale(1);
                }
                50% {
                    opacity: 1;
                    transform: scale(1.2);
                }
                100% {
                    opacity: 1;
                    transform: scale(1);
                }
            }


            .rating .star:hover {
                transform: scale(1.1);
            }
            textarea {
                width: 100%;
                background: var(--light);
                padding: 1rem;
                border-radius: .5rem;
                border: none;
                outline: none;
                resize: none;
                margin-bottom: .5rem;
            }
            .btn-group {
                display: flex;
                grid-gap: .5rem;
                align-items: center;
            }
            .btn-group .btn {
                padding: .75rem 1rem;
                border-radius: .5rem;
                border: none;
                outline: none;
                cursor: pointer;
                font-size: .875rem;
                font-weight: 500;
            }
            .btn-group .btn.submit {
                background: var(--blue);
                color: var(--white);
            }
            .btn-group .btn.submit:hover {
                background: var(--blue-d-1);
            }
            .btn-group .btn.cancel {
                background: var(--white);
                color: var(--blue);
                
            }
            .btn-group .btn.cancel:hover {
                background: var(--light);
            }
        </style>
    </head>
    <body>

        <div class="wrapper">
            <h3>Rate the tutor</h3>
            <label for="tutorName" style="margin-bottom: 20px;">Tutor Name: ${tutorName}</label>
            <br>
            <label for="subject">Subject: ${subject}</label>
            <form id="ratingForm" action="<%=request.getContextPath()%>/RatingTutorServlet" method="post">
                <div class="rating">
                    <input type="hidden" name="rating" value="0">
                    <i class='bx bx-star star' style="--i: 1;"></i>
                    <i class='bx bx-star star' style="--i: 2;"></i>
                    <i class='bx bx-star star' style="--i: 3;"></i>
                    <i class='bx bx-star star' style="--i: 4;"></i>
                    <i class='bx bx-star star' style="--i: 5;"></i>
                </div>
                <textarea name="review" id="review" cols="30" rows="5" placeholder="Your opinion..."></textarea>
                <input type="hidden" name="learnerId" value="${learnerId}" />
                <input type="hidden" name="tutorId" value="${tutorId}" />
                <input type="hidden" name="classId" value="${classId}" />
                <div class="btn-group">
                    <button type="submit" class="btn submit">Submit</button>
                    <button type="button" class="btn cancel" onclick="window.location.href = 'ViewClassnew'">Cancel</button>
                </div>
            </form>
        </div>
        <c:if test="${not empty message}">
            <script>
                alert('${message}');
                if (${success}) {
                    window.location.href = 'ViewClassnew';
                } else {
                    window.location.href = 'RatingTutorServlet?classId=${classId}';
                }
            </script>
        </c:if>

        <script>
            const allStar = document.querySelectorAll('.rating .star')
            const ratingValue = document.querySelector('.rating input')

            allStar.forEach((item, idx) => {
                item.addEventListener('click', function () {
                    let click = 0
                    ratingValue.value = idx + 1

                    allStar.forEach(i => {
                        i.classList.replace('bxs-star', 'bx-star')
                        i.classList.remove('active')
                    })
                    for (let i = 0; i < allStar.length; i++) {
                        if (i <= idx) {
                            allStar[i].classList.replace('bx-star', 'bxs-star')
                            allStar[i].classList.add('active')
                        } else {
                            allStar[i].style.setProperty('--i', click)
                            click++
                        }
                    }
                })
            })
        </script>

    </body>
</html>
