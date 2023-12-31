
package com.example.stock.controller.stock;

import com.example.stock.domain.common.CommonRes;
import com.example.stock.service.stock.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PutMapping("/api/bookmarks/{bookmarkId}")
    public ResponseEntity<CommonRes> editBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.editBookmark(bookmarkId);
        return ResponseEntity.ok(CommonRes.successRes("배정상태 변경 성공"));
    }

    @DeleteMapping("/api/bookmarks/{bookmarkId}")
    public ResponseEntity<CommonRes> removeBookmark(@PathVariable Long bookmarkId) {
        bookmarkService.removeBookmark(bookmarkId);
        return ResponseEntity.ok(CommonRes.successRes("관심종목 삭제 성공"));
    }

}
