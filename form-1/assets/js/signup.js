// function formSubmitHandle( inputType ) {

//     var form = $( "#" + inputType );
//     var inputField = getField(inputType);

//     form.submit(function(event) {
//         var dataOkay = true;

//         if( inputType === InputType.username ) {
//             // If the password was wrong, raise an alert
//             if( inputField.val() !== inputField.attr('placeholder') ) {
//                 resetAfterMistake( inputField );
//                 dataOkay = false;
//                 return false;
//             }
//         } else if( inputType === InputType.password ) {
//             // Ensure the passwords match
//             var passwordField = $("#password");
//             var passwordRepeatField = $("#passwordRepeat");
//             if( passwordField.val() !== passwordRepeatField.val() ) {
//                 // Reset the passwords
//                 passwordField.val('');
//                 passwordRepeatField.val('');

//                 // Insert an alert
//                 console.log("Error: passwords don't match");
//                 if( $('.alert').length === 0 ) {
//                     passwordField.parent().parent().before(
//                         '<div class="alert alert-block alert-error fade in pull-right">'
//                             + '<button type="button" class="close" data-dismiss="alert" data-close="bindHelpPopup($(#' + inputField.attr('id') + '))">×</button>'
//                             + '<h4 class="alert-heading">Passwords do not match</h4>'
//                             + '<p>We\'ve reset the form so that you can retype your password.</p>'
//                             + '<p>'
//                             + '<a class="btn" href="#" onclick="$(\'.alert\').alert(\'close\');">OK</a>'
//                             + '</p>'
//                             + '</div>');
//                 }
//                 return false;
//             }
//         }// similarly handle for others, i have not added all.

//         // Add the invisible field which will allow us to send timing data
//         if( dataOkay ) {
//             // If this is an account creation or account training, set the keystroke data 
//             if( inputType === InputType.//CREATE || inputType === InputType.//TRAIN ) {
//                 getTimingField(InputType.//monitoredFIeld).val(serialisedEntiredata(monitoredField));
//             }

//             // Set the key phrase's timing data
//             getTimingField(inputType).val(serialisedEntiredata(inputType));
//             return true;
//         } else {

//             return false;
//         }
//     });
// }// this is just a prototype add if something is missing

function onSignUp(form)
{
	//here
  window.open('signin.html')
}
function doAjaxPost() {
    // get the form values
	var frm = $('#signUpForm');
    $.ajax({
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        type: frm.attr('method'),
        url: frm.attr('action'),
        data: ConvertFormToJSON(frm),
        success: function(response){
            // we have the response
            if(response.status == "SUCCESS"){
                alert('success');
             }else{
                 alert('failure');
             }
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
}
function ConvertFormToJSON(form){
    var array = jQuery(form).serializeArray();
    var json = {};
    
    jQuery.each(array, function() {
        json[this.name] = this.value || '';
    });
    
    return json;
}
