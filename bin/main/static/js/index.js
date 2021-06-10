var main = {

    init: function () {
        console.log("init한다.");
        var _this = this;//객체의 메소드안에서 사용된 this는 해당 메소드를 호출한 객체에 바인딩된다. (즉 main을 호출한 객체가됨)
        $('#btn-list').on('click', function(){
            _this.list();
        })
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        })

        $('#btn-delete').on('click', function(){
            _this.delete();
        })
    },
    list: function () {
        console.log("list function!");
        var cat_id = this.name;
        cat_id = cat_id.split('-')[1]; //카테고리 이름 가져오기
        console.log("cat_id=" + cat_id);

        $.ajax({
            type: 'GET',
            url: 'api/v1/post/list/' + cat_id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
            // data: JSON.stringify(data)
        }).done(function(response) {
            alert(JSON.parse(response));
        }).fail(function (error) {
                alert(JSON.stringify(error));
        })

    },
    save: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/post',
            dataType: 'json', //서버측에서 전송받은 데이터 타입
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data) //직렬화해서 보냄
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/'; //js에서 url로 페이지 이동방법(cf. 페이지 이름으로 이동)
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/post/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function() {
        var id=$('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/post/'+id,
            contentType: 'application/json; charset=utf-8',
        }).done(function(){
            alert('글이 삭제되었습니다.');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};
console.log("index.js로드 됨.");
main.init();