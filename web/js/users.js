/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('#frmUser').validate({
        rules: {
            username: {
                required: true,
                rangelength: [3, 20]
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 5
            },
            gender: {
                //required: true,
                maxlength: 1
            },
            role: {
                //required: true,
                digits: true
            },
            company: {
                //required: true
            },
            phone: {
                digits: true,
                rangelength: [7, 10]
            },
            cellphone: {
                digits: true,
                rangelength: [10, 13]
            },
            street: {
                //required: true
            },
            streetnumber: {
                //required: true,
                digits: true
            },
            neighborhood: {
                //required: true
            },
            zipcode: {
                digits: true
            },
            city: {
                //required: true
            },
            state: {
                //required: true
            },
            region: {
                
            },
            country: {
                //required: true
            },
            photo: {
                
            }
        },
        messages: {
            username: {
                required: 'El nombre de usuario es obligatorio.',
                rangelength: 'El nombre de usuario debe ser entre 3 y 20 caracteres.'
            },
            email: {
                required: 'El correo electrónico es obligatorio.',
                email: 'El formato del correo electrónico es incorrecto.'
            },
            password: {
                required: 'La contraseña es obligatoria.',
                minlength: 'Debe tener más de cinco caracteres.'
            },
            gender: {
                required: 'El género es obligatorio.',
                maxlength: 'Debe tener máximo un caracter.'
            },
            role: {
                required: 'El rol es obligatorio.',
                digits: 'Debe introducir sólo números.'
            },
            company: {
                required: 'La compañía es obligatoria.'
            },
            phone: {
                rangelength: 'El teléfono debe tener entre 7 y 10 caracteres.',
                digits: 'Debe introducir sólo números.'
            },
            cellphone: {
                rangelength: 'El telefono celular debe tener entre 10 y 13 cracteres.',
                digits: 'Debe introducir sólo números.',
            },
            street: {
                required: 'La calle es obligatoria.'
            },
            streetnumber: {
                required: 'El número es obligatorio.',
                digits: 'Debe introducir sólo números.'
            },
            neighborhood: {
                required: 'La colonia es obligatoria.'
            },
            zipcode: {
                digits: 'Debe introducir sólo números.'
            },
            city: {
                required: 'La ciudad es obligatoria.'
            },
            state: {
                required: 'El estado es obligatorio.'
            },
            region: {
                
            },
            country: {
                required: 'El país es obligatorio.'
            },
            photo: {
                
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        },
        submitHandler: function (form) {
            newUser();
            return false;
        }
    });
});

function newUser() {
    $.ajax({
       url: 'CrearUsuario' ,
       type: 'post',
       data: $('#frmUser').serialize()
    })
    .done(function(data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#email').val('');
        $('#username').val('');
        $('#password').val('');
        $('#photo').val('');
        $('#company').val('');
        $('#gender').val('');
        $('#role').val('');
        $('#city').val('');
        $('#state').val('');
        $('#country').val('');
        $('#region').val('');
        $('#streetnumber').val('');
        $('#zipcode').val('');
        $('#phone').val('');
        $('#cellphone').val('');
        $('#neighborhood').val('');
        $('#street').val('');
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.message
        });
    });
}