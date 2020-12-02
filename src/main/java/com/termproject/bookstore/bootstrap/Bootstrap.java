package com.termproject.bookstore.bootstrap;

import com.termproject.bookstore.models.Admin;
import com.termproject.bookstore.models.Book;
import com.termproject.bookstore.models.Promotion;
import com.termproject.bookstore.models.User;
import com.termproject.bookstore.repositories.AdminRepository;
import com.termproject.bookstore.repositories.BookRepository;
import com.termproject.bookstore.repositories.PromotionRepository;
import com.termproject.bookstore.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PromotionRepository promotionRepository;

    public Bootstrap(AdminRepository adminRepository, UserRepository userRepository, BookRepository bookRepository, PromotionRepository promotionRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // initializing admin
        Admin joe = new Admin();
        joe.setId(81123353);
        joe.setFirstName("Joe");
        joe.setLastName("Shmoe");
        joe.setAdminPW("explodingkittens559");

        adminRepository.save(joe);

        // initializing users
        User user1 = new User();
        user1.setUserName("realfglez");
        user1.setEmail("realfglez@gmail.com");
        user1.setUserPW("ade123sq");
        user1.setFirstName("Frank");
        user1.setLastName("Gonzalez");
        user1.setIsEmployee(true);
        user1.setIsSubscribed(true);

        userRepository.save(user1);

        User user2 = new User();
        user2.setUserName("daeq72");
        user2.setEmail("fake_user2@gmail.com");
        user2.setUserPW("faf23912s");
        user2.setFirstName("Eric");
        user2.setLastName("Andre");
        user2.setIsEmployee(true);
        user2.setIsSubscribed(false);

        userRepository.save(user2);

        User user3 = new User();
        user3.setUserName("af3ddda");
        user3.setEmail("fake_user3@gmail.com");
        user3.setUserPW("af23r19");
        user3.setFirstName("Random");
        user3.setLastName("Guy");
        user3.setIsEmployee(false);
        user3.setIsSubscribed(false);

        userRepository.save(user3);

        User user4 = new User();
        user4.setUserName("pingalo");
        user4.setEmail("fake_user4@gmail.com");
        user4.setUserPW("pjjkali23");
        user4.setFirstName("Ronald");
        user4.setLastName("McDonald");
        user4.setIsEmployee(false);
        user4.setIsSubscribed(false);

        userRepository.save(user4);

        // initializing books
        Book book1 = new Book();
        book1.setAuthor("J.K. Rowling");
        book1.setTitle("Harry Potter");
        book1.setCategory("Fantasy");
        book1.setEdition(1);
        book1.setBuyPrice(7.99);
        book1.setSellPrice(12.99);
        book1.setMinimumThreshold(10);
        book1.setIsArchived(false);
        book1.setPublisher("Big Name Pubs");
        book1.setPublicationYear(2005);
        book1.setQuantityInStock(16);
        book1.setCoverPicURL("harry-potter.png");

        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setAuthor("Someguy Whowrites");
        book2.setTitle("A Good Book: The Sequel");
        book2.setCategory("Nonfiction");
        book2.setEdition(2);
        book2.setBuyPrice(4.99);
        book2.setSellPrice(10.99);
        book2.setMinimumThreshold(5);
        book2.setIsArchived(false);
        book2.setPublisher("Steal Publishing");
        book2.setPublicationYear(2019);
        book2.setQuantityInStock(32);
        book2.setCoverPicURL("a-good-book.png");

        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setAuthor("Isaac Asimov");
        book3.setTitle("Foundation");
        book3.setCategory("Science Fiction");
        book3.setEdition(1);
        book3.setBuyPrice(6.99);
        book3.setSellPrice(8.99);
        book3.setMinimumThreshold(20);
        book3.setIsArchived(false);
        book3.setPublisher("Penguin");
        book3.setPublicationYear(1967);
        book3.setQuantityInStock(28);
        book3.setCoverPicURL("foundation.png");

        bookRepository.save(book3);

        // initializing promotions
        Promotion promotion1 = new Promotion();
        promotion1.setPercentage(30);
        promotion1.setStartDate("2020-11-21");
        promotion1.setEndDate("2020-12-18");

        promotionRepository.save(promotion1);

        Promotion promotion2 = new Promotion();
        promotion2.setPercentage(50);
        promotion2.setStartDate("2020-11-24");
        promotion2.setEndDate("2021-01-01");

        promotionRepository.save(promotion2);

        Promotion promotion3 = new Promotion();
        promotion3.setPercentage(25);
        promotion3.setStartDate("2020-10-07");
        promotion3.setEndDate("2020-11-28");

        promotionRepository.save(promotion3);

        System.out.println("Initialized data");
        System.out.println("Number of Admins:" + adminRepository.count());
        System.out.println("Number of Users:" + userRepository.count());
        System.out.println("Number of Books:" + bookRepository.count());
        System.out.println("Number of Promotions:" + promotionRepository.count());
    }
}
