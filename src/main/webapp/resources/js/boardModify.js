console.log("boardModify Join Success");

document.addEventListener('click', (e)=>{
   if(e.target.classList.contains('file-x')){
      let uuid = e.target.dataset.uuid;
      removeFileToServer(uuid).then(result =>{
         if(result == "1"){
            e.target.closest('li').remove();
            alert("파일 삭제 성공");
         };
      });
   };
});

async function removeFileToServer(uuid){
   try {
      const url = "/board/file/" + uuid;
      const config = {
         method:"delete"
      };

      const resp = await fetch(url,config);
      const result = await resp.text();
      return result;

   } catch (error) {
      console.log(error);
   };
};