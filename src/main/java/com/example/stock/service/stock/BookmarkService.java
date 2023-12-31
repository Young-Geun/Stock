package com.example.stock.service.stock;

import com.example.stock.domain.stock.Bookmark;
import com.example.stock.domain.stock.BookmarkRes;
import com.example.stock.domain.stock.Stock;
import com.example.stock.domain.stock.StockRes;
import com.example.stock.domain.user.User;
import com.example.stock.repository.stock.BookmarkRepository;
import com.example.stock.repository.stock.StockRepository;
import com.example.stock.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final BookmarkRepository bookmarkRepository;

    public List<BookmarkRes> findBookmarks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("사용자가 없습니다."));
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUser(user);

        return bookmarks.stream()
                .map(BookmarkRes::new)
                .collect(Collectors.toList());
    }

    public List<StockRes> findUnBookmarks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("사용자가 없습니다."));
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUser(user);
        List<Stock> stocks = stockRepository.findAllByStockIdNotIn(bookmarks.stream().map(x -> x.getStock().getStockId()).collect(Collectors.toList()));

        return stocks.stream()
                .map(StockRes::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookmarkRes saveBookmark(Long userId, Long stockId) {
        User users = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("사용자가 없습니다."));
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new NoSuchElementException("공모주가 없습니다."));

        Bookmark bookmark = Bookmark.builder()
                .stock(stock)
                .user(users)
                .build();

        return new BookmarkRes(bookmarkRepository.save(bookmark));
    }

    @Transactional
    public void editBookmark(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new NoSuchElementException("관심종목이 없습니다."));
        bookmark.setIssued("Y");
    }

    @Transactional
    public void removeBookmark(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new NoSuchElementException("관심종목이 없습니다."));
        bookmarkRepository.deleteById(bookmarkId);
    }

}
