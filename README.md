# 15 Puzzle

This repository is a final project (Java GUI) from Object-Oriented Programming Class, Teknik Informatika Universitas Padjadjaran.

[Challenge Guidelines](challenge-guideline.md)

## Deskripsi Project

15 Puzzle adalah sebuah game sliding puzzle yang terdiri dari beberapa tile bernomor yang tersusun secara acak. Terdapat 1 tile yang hilang sebagai space untuk menggeser tiles bernomor. Game ini bertujuan untuk menyusun tile bernomor acak menjadi berurutan.

Ukuran puzzle ini 4x4 dengan 1 tile kosong sehingga berjumlah 15 sehingga disebut puzzle 15. Selain itu ada beberapa variasi lain seperti 8 Puzzle yang merupakan versi 3x3 dari 15 Puzzle.

## Credits

| NPM          | Name                       |
| ------------ | -------------------------- |
| 140810200001 | Ariq Hakim Ruswadi         |
| 140810200041 | Alvaro Dwi Oktaviano       |
| 140810200049 | Rafiansyah Rasyid Wikawang |

## Change log

- **[Sprint Planning](changelog/sprint-planning.md) - (20/11/2021)**

  - Inisialisasi projek gradle

- **[Sprint 1](changelog/sprint-1.md) - (20/11/2021 - 22/11/2021)**

  - N/A

- **[Sprint 2](changelog/sprint-2.md) - (24/11/2021 - 29/11/2021)**

  - Implementasi FXML untuk Launcher dan Puzzle
  - Implementasi Board dan Cell (untuk slider puzzle)
  - Implementasi GameConfig dan Session (untuk menyimpan data permainan)
  - Implementasi Stopwatch
  - Implementasi MoveCounter
  - Implementasi algoritma pengecekan isSolvable() pada puzzle
  - Implementasi shuffling dan reset button
  - Implementasi komponen pada Launcher (combobox dan image picker)
  - Implementasi custom image loader pada puzzle
  - Implementasi transfer data dari Launcher ke Puzzle
  - Implementasi fitur reset puzzle
  - Implementasi fitur refresh puzzle
  - Implementasi fitur pause dan resume puzzle

- **[Sprint 3](changelog/sprint-3.md) - (x - x)**

  - Short changes 1
  - Short changes 2

## Running The App

Aplikasi kami dapat dijalankan dengan cara berikut :

```bash
gradlew run
```

Ketika dijalankan seperti ini, puzzle akan menampilkan window Launcher untuk player melakukan konfigurasi puzzle.

## Classes Used

Kami menggunakan package terpisah untuk memudahkan pengembangan aplikasi.
Ada 4 package utama, yaitu :

### 1. **controller**

Berisi controller yang dihubungkan ke tampilan fxml UI.Kami menggunakan arsitektur MVC dengan mengikuti guidelines dari javaFX.

- 1.1 **LauncherController** - [`LauncherController.java`](src/main/java/team/emergence/_15puzzle/controller/LauncherController.java)

  Controller untuk window Launcher, berisi kode untuk memilih konfigurasi game puzzle seperti memilih _difficulty_ serta gambar yang akan dimainkan.

- 1.2 **PuzzleController** - [`PuzzleController.java`](src/main/java/team/emergence/_15puzzle/controller/PuzzleController.java)

  Controller untuk window Puzzle, berisi kode untuk game puzzle.

### 2. **core**

Berisi komponen logika yang dipakai dalam puzzle.

- 2.1. **Board** - [`Board.java`](src/main/java/team/emergence/_15puzzle/core/Board.java)

  Komponen dasar untuk game slider, didalamnya ada logika untuk mengecek apakah puzzle bisa diselesaikan, apakah puzzle bisa diklik dan apakah puzzle telah terselesaikan.

- 2.2. **BoardState** - [`BoardState.java`](src/main/java/team/emergence/_15puzzle/core/BoardState.java)

  Interface yang digunakan board untuk menjalankan kode dari luar Board. Di dalamnya diimplementasi dua state, yaitu onBoardClicked (dijalankan saat elemen puzzle diklik) serta onBoardSolved (dijalankan saat puzzle telah terselesaikan)

- 2.3. **MoveCounter** - [`MoveCounter.java`](src/main/java/team/emergence/_15puzzle/core/MoveCounter.java)

  Komponen yang digunakan untuk menghitung banyak gerakan dalam puzzle. Berisi logika untuk menghitung move saat pengguna mengerakkan puzzle.

- 2.4. **Stopwatch** - [`Stopwatch.java`](src/main/java/team/emergence/_15puzzle/core/Stopwatch.java)

  Komponen yang digunakan untuk mengitung waktu lamanya puzzle berjalan. Berisi logika untuk start, pause, resume, dan stop.

### 3. **model**

Berisi class untuk menyimpan data dalam aplikasi

- 3.1. **GameConfig** - [`GameConfig.java`](src/main/java/team/emergence/_15puzzle/model/GameConfig.java)

  Model untuk menyimpan konfigurasi dari game, berisi difficulty serta path untuk image yang dipakai. Model ini yang dioper dari Launcher ke dalam Puzzle serta disimpan dalam Session.

- 3.2. **Session** - [`Session.java`](src/main/java/team/emergence/_15puzzle/model/Session.java)

  Model untuk menyimpan data saat puzzle berjalan, berisi MoveCounter, Stopwatch, dam GameConfig yang digunakan untuk menjalankan puzzle.

- 3.3. **Cell** - [`Cell.java`](src/main/java/team/emergence/_15puzzle/core/Cell.java)

  Komponen model untuk game slider, didalamnya berisi koordinat dari kepingan puzzle yang bisa digeser serta data posisi asli dari kepingan tersebut.

### 4. **util**

Berisi class untuk menyimpan data dalam aplikasi

- 4.1. **Constants** - [`Constants.java`](src/main/java/team/emergence/_15puzzle/util/Constants.java)

  Class static untuk menyimpan data konstan, dalam aplikasi kami hanya berisi SCENE_WIDTH dan SCENE_HEIGHT.

- 4.2. **ResourceLoader** - [`ResourceLoader.java`](src/main/java/team/emergence/_15puzzle/util/ResourceLoader.java)

  Class static untuk membantu loading resource menggunakan getClassPath().getResource().

- 4.3. **LineToAbs** - [`animation/LineToAbs.java`](src/main/java/team/emergence/_15puzzle/util/animation/LineToAbs.java)

  Implementasi dari class LineTo JavaFX, dipakai untuk menggambar Line Path; bagian dari animasi menggunakan PathTransition.

- 3.4. **MoveToAbs** - [`animation/MoveToAbs.java`](src/main/java/team/emergence/_15puzzle/util/animation/MoveToAbs.java)

  Implementasi dari class MoveTo JavaFX, dipakai untuk menggerakan elemen yang telah memiliki Line Path; bagian dari animasi menggunakan PathTransition.

### Komponen lain

- 5.1. **App** - [`App.java`](src/main/java/team/emergence/_15puzzle/App.java)

  Class main, implementasi javafx.Application. Digunakan untuk memanggil Launcher

- 3.2. **Launcher.fxml** - [`Launcher.fxml`](src/main/resources/team/emergence/_15puzzle/fxml/Launcher.fxml)

  UI dari Launcher, dibuat menggunakan Gluon Scene Builder.

- 3.3. **Puzzle.fxml** - [`Puzzle.fxml`](src/main/resources/team/emergence/_15puzzle/fxml/Puzzle.fxml)

  UI dari Puzzle, dibuat menggunakan Gluon Scene Builder.

UML image here

## Notable Assumption and Design App Details

- Ada 2 window dalam aplikasi ini, yaitu Launcher dan Puzzle
  - Window Launcher berisi konfigurasi game yaitu memilih tingkat kesulitan atau jumlah grid serta memilih gambar yang akan dimainkan
  - Window Puzzle berisi tampilan Puzzle
- Rekomendasi kesulitan game adalah 3x3, 4x4, dan 5x5.
- Gambar preset disediakan 3 jenis gambar tetapi user bisa memasukkan gambar custom secara manual dengan memilih 'Import Image'.
- Gambar custom akan di stretch untuk dijadikan bentuk persegi.
- Resolusi PuzzleGrid paling optimal untuk penggunaan 2 argumen adalah 1280x720.
- Game memiliki fitur **move counter** dan **timer** sebagai tambahan.
- Ketika puzzle dimainkan, akan ada **example** yang merupakan hasil akhir ketika puzzle tersusun dengan rapih.
- Jika ingin mengganti mode, user tinggal menekan tombol 'Back to Menu' dan memilih kembali mode yang ingin dimainkan.
- Terdapat tombol **Reset** yang dapat digunakan untuk mengulang puzzle yang sama.
- Terdapat tombol **Refresh** yang dapat digunakan untuk mengacak puzzle.
- Terdapat tombol **Pause** yang dapat digunakan untuk menghentikan puzzle sementara. Saat di **pause**, player tidak bisa menggerakan puzzle dan timer berhenti sementara.
