<%-- 
    Document   : haha
    Created on : Jun 4, 2024, 10:38:50 PM
    Author     : TRANG
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<script src='newjavascript.js'></script>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var events = [
      <c:forEach items="${vector}" var="v">
        {
          title: '${v.getAClass().getTutor().getSubject().getName()}-${v.getAClass().getTutor().getName()}',
          start: '${v.getDate()}T${v.getSession().getStartTime()}',
          url: 'http://google.com/',
          end: '${v.getDate()}T${v.getSession().getEndTime()}',
          className: 'custom-event'
          
        }${not empty v and v != vector[vector.size() - 1] ? ',' : ''}
      </c:forEach>
    ];

    var calendar = new FullCalendar.Calendar(calendarEl, {
      height: '100%',
      expandRows: true,
      slotMinTime: '08:00',
      slotMaxTime: '21:00',
      headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
      },
      initialView: 'dayGridMonth',
      initialDate: new Date().toISOString().split('T')[0], // Current date
      navLinks: true, // can click day/week names to navigate views
      editable: false,
      selectable: false,
      nowIndicator: true,
      dayMaxEvents: true, // allow "more" link when too many events
      events: events
    });

    calendar.render();
  });
</script>
<style>
  html, body {
    overflow: hidden; /* don't do scrollbars */
    font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
    font-size: 14px;
  }

  #calendar-container {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }

  .fc-header-toolbar {
    /*
    the calendar will be butting up against the edges,
    but let's scoot in the header's buttons
    */
    padding-top: 1em;
    padding-left: 1em;
    padding-right: 1em;
  }
</style>
</head>
<body>

  <div id='calendar-container'>
    <div id='calendar'></div>
  </div>

</body>
</html>
