$(document).ready(function() {
	(function () {
		'use strict';
		var form = document.getElementById('login_form');
		form.addEventListener('change', function (event) {
			if (form.checkValidity() === false) {
				event.preventDefault();
				event.stopPropagation();
				document.getElementById('login_button').setAttribute('disabled', 'disabled');
			} else {
				document.getElementById('login_button').removeAttribute('disabled');
			}				
			form.classList.add('was-validated');
		}, false);
	})();

	$("#invalidCheck").click( function () {
		if ($( '#invalidCheck' ).prop( 'checked')) {
			$( '#register_btn' ).prop( 'disabled', false );
		} else {
			$( '#register_btn' ).prop( 'disabled', true );
		}
	});

	$('#msgCharsLeft').text('32 characters left');
	$("#address").keyup( function () {
		const maxChars = 32;
		var msgLength = $(this).val().length;

		if ( msgLength >= maxChars ) {
			$('#msgCharsLeft').text('You have reached the limit of ' + maxChars + ' characters.');
			// change the color of the message (to red)
			$('#msgCharsLeft').addClass('msgCharLimitColor'); // this class is style.css
		} else {
			var left = maxChars - msgLength;
			$('#msgCharsLeft').text( left + ' character' + (left>1?'s':'') + ' left' );
			$('#msgCharsLeft').removeClass('msgCharLimitColor');
		}
	} );
});