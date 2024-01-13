console.log("boardComment.js Join Success");
console.log(bnoVal);

document.getElementById('cmtPostBtn').addEventListener('click', ()=>{
    
    const cmtText = document.getElementById('cmtText');
    if(cmtText.value == null || cmtText.value == ''){
        alert('댓글을 입력해 주세요.');
        cmtText.focus();
        return;
    }else{
        let cmtData = {
            bno:bnoVal,
            writer:document.getElementById('cmtWriter').innerText,
            content:cmtText.value
        };
        console.log(cmtData);

        postCommentToServer(cmtData).then(result =>{
            if(result === '1'){
                alert("댓글 등록 성공");
                cmtText.value = '';
                // 댓글 뿌리기
                spreadCommentList(bnoVal);
            }
        });

    }
});

async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post"
        const config = {
            method:"post",
            headers:{
                'content-type':'application/json; charset=UTF-8'
            },
            body:JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
};

async function getCommentListFromServer(bno, page){
    // 더보기 버튼을 위한 page 매개변수 추가
    try {
        const resp = await fetch("/comment/" + bno + "/" + page);
        const result = await resp.json(); // PagingHandler
        // 비동기는 객체를 여러개 전송할 수 없다. (동기는 가능)
        // 따라서 JSON으로 통합해서 모든 정보를 한번에 전송해야 함
        // 그래서 페이징핸들러에 cmtList 멤버변수를 추가하여
        // 페이징핸들러가 모든 정보를 가지고 있도록 setting한 후
        // 페이징핸들러 객체 하나로 모든 정보를 주고 받고 해야한다.
        return result;
    } catch (error) {
        console.log(error);
    }
};

function spreadCommentList(bno, page = 1){
    // 더보기 버튼은 댓글이 뿌려질 때 항상 page가 1이다.
    // => 첫 화면에 보여지는 moreBtn의 data-page는 항상 1
    // 따라서 page = 1 매개변수를 추가해야 됨

    getCommentListFromServer(bno, page).then(result =>{
        console.log(result); // result : PagingHandler
        const ul = document.getElementById('cmtListArea');

        if(result.cmtList.length > 0){
            // 더보기 버튼 클릭할 때 기존 cmtList 목록을 삭제하면 안됨
            // 왜? 더보기 버튼을 클릭할 때마다 초기화되면 앞쪽 댓글은 보이지 않게 됨
            // 단, page=1 일 경우(맨 처음 화면에 출력될 때)만 초기화하여 댓글을 처음부터 출력
            // 따라서 하단의 if문으로 page=1 일때 댓글 목록을 한번 초기화 해준다.
            if(page == 1){
                ul.innerHTML = '';
            };

            // Comment List Code
            for(let cvo of result.cmtList){
                let li = ``;
                li += `<li class="list-group-item" data-cno="${cvo.cno}" data-writer="${cvo.writer}">`;
                li += `<div class="mb-3">`;
                li += `<div class="fw-bold">${cvo.writer}</div>`;
                li += `${cvo.content}`;
                li += `</div>`;
                li += `<span class="badge rounded-pill text-bg-success">${cvo.modAt}</span>`;
                li += `<button type="button" data-cno="${cvo.cno}" class="btn htn-sm btn-outline-primary mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                // 댓글 수정 버튼을 모달창과 연결시켜야 함 => 버튼 태그에 data-bs-toggle="modal" data-bs-target="#myModal" 추가해야 됨
                // data-bs-target에 모달창 div의 id를 입력하여 연동시켜야 함. e.g.)data-bs-target="#모달창id">
                li += `<button type="button" data-cno="${cvo.cno}" class="btn htn-sm btn-outline-danger del">삭제</button>`;
                li += `</li>`;
                ul.innerHTML += li;
            };

            // 더보기 버튼 Code
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            // more 버튼 표시 조건
            if(result.pgvo.pageNo < result.endPage){
                moreBtn.style.visibility = 'visible'; // More 버튼 표시 활성화
                moreBtn.dataset.page = page + 1;
                // More 버튼이 활성화된다는 것은 다음 page가 존재한다는 것임 => page+1 필요
            }else{
                // result.pgvo.pageNo 와 result.endPage 값이 같아질 때...
                // => 다음 page가 존재하지 않음
                moreBtn.style.visibility = 'hidden'; // 버튼 표시 비활성화(숨김)
            }

        }else{
            // 댓글이 하나도 없다면...
            let li = `<li class="list-group-item">Comment List Empty</li>`;
            ul.innerHTML = li;
        }
    })
};

document.addEventListener('click', (e)=>{
    console.log(e.target);

    if(e.target.id == 'moreBtn'){
        // 더보기 버튼
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);

    }else if(e.target.classList.contains('mod')){
        // 댓글 수정 버튼
        let li = e.target.closest('li');
        let cmtText = li.querySelector('.fw-bold').nextSibling; // 수정 전 cmtText 값
        console.log(cmtText);
        // nextSibling : 한 부모 내부에서 같은(다음) 형제를 찾아라

        // 모달창에 기존의 댓글 내용을 반영 (수정하기 편하도록...)
        document.getElementById('cmtTextModal').value = cmtText.nodeValue;

        // 수정을 하기 위해선 cno, writer, content 3가지 data가 필요함
        // cno & writer를 data- 로 달아주기
        document.getElementById('cmtEditBtn').setAttribute("data-cno", li.dataset.cno);
        document.getElementById('cmtEditBtn').setAttribute("data-writer", li.dataset.writer);

    }else if(e.target.id == 'cmtEditBtn'){
        // 모달창의 Edit 버튼
        let cmtDataEdit={
            cno:e.target.dataset.cno,
            writer:e.target.dataset.writer,
            content:document.getElementById('cmtTextModal').value
        };
        console.log(cmtDataEdit);
        
        // Edit 비동기 통신
        editCommentToServer(cmtDataEdit).then(result =>{
            if(result == "1"){
                // 댓글 수정 성공
                // 모달창 닫기(닫기 버튼 클릭)
                document.querySelector('.btn-close').click();
                alert('댓글 수정 성공');
            }else{
                document.querySelector('.btn-close').click();
                alert('댓글 수정 실패');
            }
            // 댓글 수정 후 댓글 뿌리기 (page=1)
            spreadCommentList(bnoVal);
        });

    }else if(e.target.classList.contains('del')){
        // 댓글 삭제 버튼
        let li = e.target.closest('li');
        let cno = li.dataset.cno;
        
        // 삭제 비동기 통신
        deleteCommentToServer(cno).then(result =>{
            if(result == 1){
                alert('댓글 삭제 성공');
            }else{
                alert('댓글 삭제 실패');
            }
            spreadCommentList(bnoVal);
        })
    }
});

// Edit 비동기 통신
async function editCommentToServer(cmtDataEdit){
    try {
        const url = '/comment/edit';
        const config={
            method:'put',
            headers:{
                'content-type':'application/json; charset=UTF-8'
            },
            body:JSON.stringify(cmtDataEdit)
        };
        const resp = await fetch(url, config);
        const result = resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
};

// 삭제 비동기 통신
async function deleteCommentToServer(cno){
    try {
        const url = '/comment/delete/' + cno;
        const resp = await fetch(url);
        const result = resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
};