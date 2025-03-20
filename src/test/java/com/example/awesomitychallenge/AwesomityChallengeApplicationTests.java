package com.example.awesomitychallenge;

import com.example.awesomitychallenge.controllers.AuthController;
import com.example.awesomitychallenge.controllers.OrderController;
import com.example.awesomitychallenge.controllers.ProductController;
import com.example.awesomitychallenge.controllers.UserController;
import com.example.awesomitychallenge.dto.CreateProductDto;
import com.example.awesomitychallenge.dto.UserDto;
import com.example.awesomitychallenge.services.OrderService;
import com.example.awesomitychallenge.services.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AwesomityChallengeApplicationTests {

    @Mock
    private UserService userService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AuthController authController;

    @InjectMocks
    private OrderController orderController;

    @InjectMocks
    private ProductController productController;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private CreateProductDto productDto;

//    @BeforeEach
//    void setUp() {
//        userDto = new UserDto();
//        userDto.setEmail("hitimanadav47@gmail.com");
//        userDto.setPassword("David123!");
//
//        OrderDto orderDto = new OrderDto();
//        orderDto.setProductName("Laptop");
//
//        productDto = new CreateProductDto();
//        productDto.setProductName("Phone");
//    }
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void testUserSignUp_Success() {
//        when(userService.userSignUp(any(UserDto.class))).thenReturn(true);
//        ResponseEntity<String> response = authController.userSignUp(userDto);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User Registered Successfully!", response.getBody());
//    }
//
//    @Test
//    void testUserLogin_Success() {
//        when(userService.login(anyString(), anyString())).thenReturn(new AuthenticationResponse("token"));
//        ResponseEntity<AuthenticationResponse> response = authController.login(Map.of("email", "hitimanadav47@gmail.com", "password", "David123!"));
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("token", response.getBody().getToken());
//    }
//
//    @Test
//    void testPlaceOrder_Success() {
//
//        String productName = "Laptop";
//        int quantity = 2;
//        doNothing().when(orderService).placeOrders(productName, quantity);
//        ResponseEntity<String> response = orderController.placeOrders(productName, quantity);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Thanks for placing an order!", response.getBody());
//    }
//
//    @Test
//    void testRegisterProduct_Success() {
//        ResponseEntity<String> response = productController.registerProduct(productDto);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Product has been registered successfully", response.getBody());
//    }
//
//    @Test
//    void testDeleteUser_Success() {
//        ResponseEntity<String> response = userController.deleteUser("");
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("User deleted successfully!", response.getBody());
//    }

}
