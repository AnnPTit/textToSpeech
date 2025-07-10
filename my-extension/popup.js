document.addEventListener("DOMContentLoaded", async () => {
  await fetchVocabularyList(); // Tự động gọi khi popup mở
});

async function fetchVocabularyList() {
  try {
    const response = await fetch("http://localhost:8080/api/vocab/self-study", {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) throw new Error("Status: " + response.status);

    const data = await response.json(); // giả sử API trả về JSON mảng
    renderList(data); // Hiển thị danh sách

  } catch (error) {
    console.error("Gọi API lỗi:", error);
    alert("Lấy danh sách thất bại");
  }
}

function renderList(words) {
  const listElement = document.getElementById("vocabList");
  listElement.innerHTML = "";

  words.forEach((item) => {
    const li = document.createElement("li");
    li.textContent = `${item.word} - ${item.meaning}`;
    listElement.appendChild(li);
  });
}

document.getElementById("clickMe").addEventListener("click", async () => {
  try {
    const engText = document.getElementById("inputEnglish").value;
    const vnText = document.getElementById("inputVietNamese").value;
    const data = { word: engText, meaning: vnText };

    const response = await fetch("http://localhost:8080/api/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Status: " + response.status);

    alert("Tạo từ mới thành công!");

    await fetchVocabularyList(); // load lại danh sách

  } catch (error) {
    console.error("Gọi API lỗi:", error);
    alert("Tạo từ thất bại");
  }
});
