    

var message = document.getElementById("message").value;

         if (message !== null && message.length > 0) {
         	swal("Thông báo!", message, "success");
         	session.removeAttribute("message"); 
         }
         
       