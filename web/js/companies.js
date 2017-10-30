/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('#frmCompany').validate({
        rules: {
            companyname: {
                required: true,
                rangelength: [3, 20]
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
            logo: {
                
            },
            phone: {
                digits: true,
                rangelength: [7, 10]
            }
        },
        messages: {
            companyname: {
                required: 'El nombre de la compañía es obligatorio.',
                rangelength: 'El nombre de la compañía debe ser entre 3 y 20 caracteres.'
            },
            phone: {
                rangelength: 'El teléfono debe tener entre 7 y 10 caracteres.',
                digits: 'Debe introducir sólo números.'
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
            logo: {
                
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
            newCompany();
            return false;
        }
    });
    
    $('#frmEditCompany').validate({
        rules: {
            companyname: {
                required: true,
                rangelength: [3, 20]
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
            logo: {
                
            },
            phone: {
                digits: true,
                rangelength: [7, 10]
            }
        },
        messages: {
            companyname: {
                required: 'El nombre de la compañía es obligatorio.',
                rangelength: 'El nombre de la compañía debe ser entre 3 y 20 caracteres.'
            },
            phone: {
                rangelength: 'El teléfono debe tener entre 7 y 10 caracteres.',
                digits: 'Debe introducir sólo números.'
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
            logo: {
                
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
            updateCompany();
            return false;
        }
    });
    
    $('#tbcompanies').DataTable({
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json'
        },
        ajax: {
            url: 'ConsultaCompaies',
            dataSrc: function (json) {
                return $.parseJSON(json.detail);
            }
        },
        columns: [
            {
                data: function (row) {
                    return "<div align = 'center'>" + row["companyid"] + "</div>";
                }   
            },
            { data: 'companyname' },
            { data: 'rfc' },
            { data: 'phone' },
            { data: 'street' },
            { data: 'streetnumber' },
            { data: 'neighborhood' },
            { data: 'city' },
            {
                data: function (row) {
                    var str = "<div align = 'center'>";
                    str += "<button class = 'btn btn-danger btn-xs'\n\
                            onclick = 'deleteCompany(" + row["companyid"] + ")'><span\n\
                            class = 'glyphicon glyphicon-trash' \n\
                            aria-hidden = 'true'></span></button>";
                    str += "&nbsp;<button class = 'btn btn-info btn-xs' \n\
                            onclick = 'showCompany(" + row + ")'>\n\
                            <span class = 'glyphicon glyphicon-pencil' \n\
                            aria-hidden = 'true'></span></button>";
                    str += "</div>";
                    return str;
                }
            }
        ]
    });
    
    $('#btnModificar').click(function () {
        $('#frmEditCategory').submit();
    });
});

function newCompany () {
    $.ajax({
       url: 'CrearCategory',
       type: 'post',
       date: $('#frmCategory').serialize()
    })
    .done(function (data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#categoryname').val('');
        $('#tbcategories').dataTable().api().ajax.reload(null, false);
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.mesage
        });
    });
}

function deleteCompany(id) {
    swal({
        title: '¿Estás seguro de eliminar la companía?',
        text: "La operación es irreversible",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar'
    }).then(function () {
        $.ajax({
            url: 'EliminarCompany',
            type: 'post',
            data: { id: id }
        })
        .done(function (data) {
            console.log(data);
            swal(
                '¡Exitoso!',
                data.message,
                'success'
                );
            $('#tbroles').dataTable().api().ajax.reload(null, false);
        })
        .fail(function (data) {
            swal(
                '¡Error!',
                data.responseJSON.message,
                'error'
                );
        });
    });
}

function showCompany(company) {
   $('#companyid').val(company.companyid);
   $('#companyname2').val(company.companyname);
   $('#rcf2').val(company.rfc);
   $('#phone2').val(company.phone);
   $('#street2').val(company.street);
   $('#streetnumber2').val(company.streetnumber);
   $('#neighborhood2').val(company.neighborhood);
   $('#city2').val(company.city);
   $('#modalCompany').modal('show');
}

function updateCompany(){
    $.ajax({
        url: 'ActualizarCompany',
        type: 'post',
        data: {
            roleid: $('#companyid').val(),
            rolename: $('#companyname2').val(),
            rfc: $('#rcf2').val(),
            phone: $('#phone2').val(),
            street: $('#street2').val(),
            streetnumber: $('#streetnumber2').val(),
            neighborhood: $('#neighborhood2').val(),
            city: $('#city2').val()
        }
    })
    .done(function (data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#tbcompanies').dataTable().api().ajax.reload(null, false);
        $('#modalCompany').modal('hide');
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.message
        });
    });    
}