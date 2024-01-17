console.log("boardRegister Join Success");

// 트리거 버튼 클릭하면 file upload 버튼 클릭
document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('files').click();
});

// 정규표현식을 활용한 업로드 금지 파일 설정
const regExp = new RegExp("\.(exe|sh|bat|dll|jar|msi)$"); // 업로드 금지 확장자 정규표현식 설정 완료
const maxFileSize = 1024*1024*20 // 20MB, 파일 최대 크기(WebConfig 설정과 동일하게)

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0; // 검증 불합격
    }else if(fileSize > maxFileSize){
        return 0; // 검증 불합격
    }else{
        return 1; // 검증 통과
    }
}

document.addEventListener('change', (e)=>{
    console.log(e.target);

    if(e.target.id == 'files'){ // register.jsp의 input type="file"에 변화가 발생한다면...
        const fileObj = document.getElementById('files').files;
        // .files : input file element에 저장되어 있는 file들의 정보를 가져오기
        // 가져온 fileObj는 배열로 구성이 됨
        console.log(fileObj);

        // Upload 버튼의 disabled가 한번이라도 true가 되었다면
        // false로 자동 복구될 수 없으므로 직접 false로 복구 시켜야 함
        document.getElementById('regBtn').disabled = false;

        // 첨부파일에 대한 정보를 fileZone에 표시 시키기
        let fileZone = document.getElementById('fileZone');
        fileZone.innerHTML = '';
        /*
            하나의 ul에 li로 각 파일의 값을 추가하여 fileZone에 표시할 것임
            //<ul class="list-group list-group-flush">
            //<li class="list-group-item"></li>

            여러개의 파일에 대한 검증을 모두 통과하기 위해서는 곱하기 연산자로
            각 파일마다 통과 여부를 확인해야 함
        */
        
        let isOK = 1; // isOK : 모든 파일의 검증 결과
        let ul = `<ul class="list-group list-group-flush">`;
        for(let file of fileObj){
            let validationResult = fileValidation(file.name, file.size);
            // validationResult : 각 파일마다 개별 검증 결과
            isOK *= validationResult
            // 개별 검증 통과되면 isOK는 1, 안되면 0
            ul += `<li class="list-group-item">`;
            ul += `<div class="mb-3">`;
            ul += `${validationResult ? '<div class="text-bg-success">업로드 가능</div>' : '<div class="text-bg-danger">업로드 불가능</div>'}`;
            ul += `${file.name}</div>`;
            ul += `<span class="badge rounded-pill text-bg-${validationResult ? 'success' : 'danger'}">${file.size}Byte</span>`;
            ul += `</li>`;
        };
        ul += `</ul>`;
        fileZone.innerHTML = ul;

        // 최종 파일 검증 확인
        if(isOK == 0){
            // 하나의 파일이라도 검증에 통과하지 못해서 isOK가 0이 된다면...
            document.getElementById('regBtn').disabled = true; // Upload 버튼 비활성화
        };
    };
});