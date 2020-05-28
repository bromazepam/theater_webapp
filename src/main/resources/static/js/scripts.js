/**
 * brisanje predstave
 */
$(document).ready(function () {
    $('.delete-play').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'remove';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Da li ste sigurni da želite da izbrišete ovu predstavu? Ova akcija se ne može poništiti.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Otkaži'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Potvrdi'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });


    $('#deleteSelected').click(function () {
        var idList = $('.checkboxPlay');
        var playIdList = [];
        for (var i = 0; i < idList.length; i++) {
            if (idList[i].checked == true) {
                playIdList.push(idList[i]['id'])
            }
        }

        console.log(playIdList);

        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'removeList';
        /*]]>*/

        bootbox.confirm({
            message: "Da li ste sigurni da želite da izbrišete selektovane predstave? Ova akcija se ne može poništiti.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Otkaži'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Potvrdi'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.ajax({
                        type: 'POST',
                        url: path,
                        data: JSON.stringify(playIdList),
                        contentType: "application/json",
                        success: function (res) {
                            console.log(res);
                            location.reload()
                        },
                        error: function (res) {
                            console.log(res);
                            location.reload();
                        }
                    });
                }
            }
        });
    });

    $("#selectAllPlays").click(function () {
        if ($(this).prop("checked") == true) {
            $(".checkboxPlay").prop("checked", true);
        } else if ($(this).prop("checked") == false) {
            $(".checkboxPlay").prop("checked", false);
        }
    })
});

/**
 * brisanje scene
 */
$(document).ready(function () {
    $('.delete-stage').on('click', function () {
        /*<![CDATA[*/
        var path = /*[[@{/}]]*/'removeStage';
        /*]]>*/

        var id = $(this).attr('id');

        bootbox.confirm({
            message: "Da li ste sigurni da želite da izbrišete ovu scenu? Ova akcija se ne može poništiti.",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Otkaži'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Potvrdi'
                }
            },
            callback: function (confirmed) {
                if (confirmed) {
                    $.post(path, {'id': id}, function (res) {
                        location.reload();
                    });
                }
            }
        });
    });
});