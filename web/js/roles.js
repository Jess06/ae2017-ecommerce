/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('#frmRole').validate({
        rules: {
            rolename: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            salary: {
                required: true
            }
        },
        messages: {
            rolename: {
                minlength: 'Debe tener más de tres caracteres.',
                maxlength: 'Debe tener menos de 20 caracteres.',
                required: 'El nombre del rol es obligatorio.'
            },
            salary: {
                required: 'El salario es obligatorio'
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
            newRole();
            return false;
        }
    });
    
    $('#frmEditRole').validate({
        rules: {
            rolename2: {
                minlength: 3,
                maxlength: 20,
                required: true
            }
        },
        messages: {
            rolename2: {
                minlength: 'Debe tener más de tres caracteres.',
                maxlength: 'Debe tener menos de 20 caracteres.',
                required: 'El nombre del rol es obligatorio.'
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
            updateRole();
            return false;
        }
    });

    $('#tbroles').DataTable({
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json'
        },
        ajax: {
            url: 'ConsultaRoles',
            dataSrc: function (json) {
                console.log(json.detail);
                return $.parseJSON(json.detail);
            }
        },
        columns: [
            {
                data: function (row) {
                    return "<div align = 'center'>" + row["roleid"] + "</div>";
                }
            },
            {data: 'rolename'},
            { 
                data: function(row){
                    return "<div align = 'center'>" + accounting.formatMoney(row["salary"]) + "</div>";
                }
            },
            
            {
                data: function (row) {
                    moment.locale('es');
                    return moment(row["createdat"]).format('LL');
                }
            },
            {
                data: function (row) {
                    //return row['roleid'];
                    var str = "<div align = 'center'>";
                    str += "<button class = 'btn btn-danger btn-xs'\n\
                            onclick = 'deleteRole(" + row["roleid"] + ")'><span\n\
                            class = 'glyphicon glyphicon-trash' \n\
                            aria-hidden = 'true'></span></button>";
                    str += "&nbsp;<button class = 'btn btn-info btn-xs' \n\
                            onclick = 'showRole(" + row["roleid"] + ",\"" + row["rolename"] + "\")'>\n\
                            <span class = 'glyphicon glyphicon-pencil' \n\
                            aria-hidden = 'true'></span></button>";
                    str += "</div>";
                    return str;
                }
            }
        ]
    });
    
    $('#btnModificar').click(function () {
        $('#frmEditRole').submit();
    });
});

function newRole() {
    $.ajax({
        url: 'CrearRol',
        type: 'post',
        data: {
            rolename: $('#rolename').val(),
            salary: $('#salary').val()
        }
    })
    .done(function (data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#rolename').val('');
        $('#tbroles').dataTable().api().ajax.reload(null, false);
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.message
        });
    });
}

function deleteRole(id) {
    swal({
        title: '¿Estás seguro de eliminar el rol?',
        text: "La operación es irreversible",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar'
    }).then(function () {
        $.ajax({
            url: 'EliminarRol',
            type: 'post',
            data: { id: id }
        })
        .done(function (data) {
            console.log(data);
//            $.growl.notice({
//                title: '¡Exitoso!',
//                message: data.message
//            });
            swal(
                '¡Exitoso!',
                data.message,
                'success'
                );
            $('#tbroles').dataTable().api().ajax.reload(null, false);
        })
        .fail(function (data) {
            //console.log(data);
//            $.growl.error({
//                title: '¡Error!',
//                message: data.responseJSON.message
//            });
            swal(
                '¡Error!',
                data.responseJSON.message,
                'error'
                );
        });
    });
}

function showRole(id, name) {
    $('#roleid').val(id);
    $('#rolename2').val(name);
    $('#modalRole').modal('show');
}

function updateRole(){
    $.ajax({
        url: 'ActualizarRol',
        type: 'post',
        data: {
            roleid: $('#roleid').val(),
            rolename: $('#rolename2').val()
        }
    })
    .done(function (data) {
        $.growl.notice({
            title: '¡Exitoso!',
            message: data.message
        });
        $('#tbroles').dataTable().api().ajax.reload(null, false);
        $('#modalRole').modal('hide');
    })
    .fail(function (data) {
        $.growl.error({
            title: '¡Error!',
            message: data.responseJSON.message
        });
    });    
}