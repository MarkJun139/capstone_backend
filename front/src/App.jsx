// App 컴포넌트
import {
  Box,
  Button,
  ChakraProvider,
  Flex,
  Image,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  useDisclosure,
  Drawer,
  DrawerBody,
  DrawerCloseButton,
  DrawerContent,
  DrawerHeader,
  DrawerOverlay,
  IconButton,
  Tooltip
} from '@chakra-ui/react';
import { FiMenu } from 'react-icons/fi';
import React, { useEffect, useState } from 'react';
import './App.css';
import CategorySelect from './CategorySelect';
import LoginForm from './LoginForm';
import Main from './Main';
import ProductDetail from './ProductDetail';
import SignupForm from './SignupForm';
import { UserProvider } from './UserContext';
import UserMenu from './UserMenu';

function App() {
  const [showLoginForm, setShowLoginForm] = useState(false);
  const [showSignupForm, setShowSignupForm] = useState(false);
  const [showMain, setShowMain] = useState(true);
  const [user, setUser] = useState(null);
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const [showProductDetail, setShowProductDetail] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const { isOpen, onOpen, onClose } = useDisclosure();

  useEffect(() => {
    const fetchSession = async () => {
      try {
        const response = await fetch('/getuser', {
          method: 'POST',
          credentials: 'include',
        });
        if (response.ok) {
          
          const data = await response.json();

          if (data.session && data.session.is_logined) {
            const newUser = {
              nickname: data.session.nickname,
            };
            setUser(newUser);
          }
        } else {
          throw new Error('HTTP 요청 실패');
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchSession();
  }, []);

  const handleLoginClick = () => {
    setShowLoginForm(true);
    setShowSignupForm(false);
    setShowMain(false);
    setShowProductDetail(false);
  };

  const handleSignupClick = () => {
    setShowLoginForm(false);
    setShowSignupForm(true);
    setShowMain(false);
    setShowProductDetail(false);
  };

  const handleLogoClick = () => {
    setShowLoginForm(false);
    setShowSignupForm(false);
    setShowMain(true);
    setSelectedProduct(null);
    setShowProductDetail(false);
    setSelectedCategory(null);
  };

  const handleLogin = async (uId, uPw) => {
    try {
      const response = await fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ uId, uPw }),
        credentials: 'include',
        withCredentials: true
      });

      const responseText = await response.text();

      if (response.status === 200) {
        console.log("테스트");
        let loginsess = response.headers.get('LoginSession');
        alert(loginsess);
        localStorage.setItem('loginsess', loginsess);

        const contentType = response.headers.get('Content-Type');
        if (contentType && contentType.includes('application/json')) {
          const data = JSON.parse(responseText);
          if (data) {
            const newUser = {
              nickname: data.nickname,
            };
            setUser(newUser);
            setShowLoginForm(false);
            setShowMain(true);
          } else {
            alert("로그인 실패");
          }
        } else {
          throw new Error('응답 본문이 JSON 형식이 아닙니다.');
        }
      } else {
        throw new Error('HTTP 요청 실패');
      }
    } catch (error) {
      console.error(error);
      alert('로그인 중 오류가 발생했습니다.');
    }
  };


  const handleLogout = async () => {
    try {
      const response = await fetch('http://localhost:3001/logout', {
        method: 'POST',
        credentials: 'include',
      });

      if (response.ok) {
        const data = await response.json();
        if (data.success) {
          setUser(null);
          setShowLogoutModal(false);
          setShowMain(true);
          setShowProductDetail(false);
        } else {
          throw new Error('로그아웃 실패');
        }
      } else {
        throw new Error('HTTP 요청 실패');
      }
    } catch (error) {
      console.error(error);
      alert('로그아웃 중 오류가 발생했습니다.');
    }
  };

  const handleProductDetailClose = () => {
    setShowMain(true);
    setShowProductDetail(false);
  };

  const handleProductDetail = (product) => {
    setSelectedProduct(product);
    setShowMain(false);
    setShowProductDetail(true);
  };

  const handleLogoutModalOpen = () => {
    setShowLogoutModal(true);
  };

  const handleLogoutModalClose = () => {
    setShowLogoutModal(false);
  };

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
  };

  return (
    <UserProvider value={{ user, logout: handleLogout }}>
      <ChakraProvider>
        <div style={{ minHeight: '100vh' }}>
          <Flex align="center" justify="space-between" p={5} className="menuBar">
            <Image
              src="/logo.png"
              alt="로고"
              boxSize="25px"
              onClick={handleLogoClick}
              style={{ cursor: 'pointer' }}
            />

            <Box>
              {user && (
                <>
                  <Tooltip label="메뉴" openDelay={500}>
                    <IconButton
                      icon={<FiMenu />}
                      onClick={onOpen}
                      colorScheme="white"
                      variant="outline"
                      backgroundColor="black"
                      color="white"
                      _hover={{backgroundColor: "gray.700"}}
                      _active={{backgroundColor: "gray.800"}}
                    />
                  </Tooltip>
                  <Drawer placement="right" onClose={onClose} isOpen={isOpen}>
                    <DrawerOverlay />
                    <DrawerContent>
                      <DrawerCloseButton />
                      <DrawerHeader>환영합니다, {user.nickname}님</DrawerHeader>
                      <DrawerBody>
                        <Button colorScheme="red" onClick={handleLogout}>
                          로그아웃
                        </Button>
                      </DrawerBody>
                    </DrawerContent>
                  </Drawer>
                </>
              )}
              {!user && (
                <Button
                  colorScheme="white"
                  variant="outline"
                  size="sm"
                  onClick={handleLoginClick}
                  _focus={{ boxShadow: 'none' }}
                  _active={{ bg: 'gray.300' }}
                >
                  로그인
                </Button>
              )}
            </Box>
          </Flex>

          {showLoginForm && (
            <Box className="boxStyle">
              <LoginForm onSignupClick={handleSignupClick} onLogin={handleLogin} />
            </Box>
          )}

          {showSignupForm && (
            <Box className="boxStyle">
              <SignupForm onClose={handleLoginClick} onSignup={() => {}} />
            </Box>
          )}

          {showMain && (
            <Flex>
              <Box flex="2" style={{ padding: '0', margin: '0' }}>
                <CategorySelect 
                onSelectCategory={handleCategorySelect} 
                selectedCategory={selectedCategory} 
                onCategoryClick={handleProductDetailClose}
              />
              </Box>
              <Box flex="8" style={{ padding: '0', margin: '0' }}>
                <Main selectedCategory={selectedCategory} onDetail={handleProductDetail} />
              </Box>
            </Flex>
          )}

{showProductDetail && (
  <Flex>
    <Box flex="2" style={{ padding: '0', margin: '0' }}>
      <CategorySelect 
        onSelectCategory={handleCategorySelect} 
        selectedCategory={selectedCategory} 
        onCategoryClick={handleProductDetailClose}
      />
    </Box>
    <Box flex="8" style={{ padding: '0', margin: '0' }}>
      <ProductDetail product={selectedProduct} onClose={handleProductDetailClose} onSelectCategory={handleCategorySelect} />
    </Box>
  </Flex>
)}

          <Modal isOpen={showLogoutModal} onClose={handleLogoutModalClose}>
            <ModalOverlay />
            <ModalContent>
              <ModalHeader>로그아웃</ModalHeader>
              <ModalCloseButton />
              <ModalBody className="modalBody">로그아웃 하시겠습니까?</ModalBody>
              <ModalFooter>
                <Button colorScheme="red" mr={3} onClick={handleLogout}>
                  확인
                </Button>
                <Button variant="ghost" onClick={handleLogoutModalClose}>
                  취소
                </Button>
              </ModalFooter>
            </ModalContent>
          </Modal>
        </div>
      </ChakraProvider>
    </UserProvider>
  );
}

export default App;
