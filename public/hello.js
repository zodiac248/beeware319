$(document).ready(function() {
    $.ajax({
        url: "https://beeware319-front.herokuapp.com/greeting"
    }).then(function(data, status, jqxhr) {
       $('.greeting-id').append(data.id);
       $('.greeting-content').append(data.content);
       console.log(jqxhr);
    });
});
