// 카테고리 가져오는 API 작업완료되면 이것으로 변경할 예정
// import React, { useState, useEffect } from 'react';
// import { Box, Button } from '@chakra-ui/react';

// const CategorySelect = () => {
//   const [categories, setCategories] = useState([]);
//   const [selectedCategory, setSelectedCategory] = useState('');

//   useEffect(() => {
//     const fetchCategories = async () => {
//       try {
//         const response = await fetch('/api/categories');
//         if (response.ok) {
//           const data = await response.json();
//           setCategories(data);
//         } else {
//           throw new Error('HTTP 요청 실패');
//         }
//       } catch (error) {
//         console.error(error);
//       }
//     };

//     fetchCategories();
//   }, []);

//   const handleCategoryChange = (category) => {
//     setSelectedCategory(category);
//   };

//   return (
//     <Box width="200px" marginRight="20px">
//       {categories.map((category) => (
//         <Button
//           key={category.id}
//           variant={selectedCategory === category.id ? 'solid' : 'outline'}
//           colorScheme="blue"
//           onClick={() => handleCategoryChange(category.id)}
//           marginBottom="10px"
//         >
//           {category.name}
//         </Button>
//       ))}
//     </Box>
//   );
// };

// export default CategorySelect;


import React from 'react';
import { Box, Button } from '@chakra-ui/react';

const CategorySelect = ({ onSelectCategory, selectedCategory, onCategoryClick }) => {
  const categories = ['티셔츠', '바지', '스커트', '드레스', '아우터'];

  const handleCategorySelect = (category) => {
    if (selectedCategory === category) {
      onSelectCategory(null);
    } else {
      onSelectCategory(category);
    }

    if (onCategoryClick) {
      onCategoryClick();
    }
  };

  const isCategorySelected = (category) => {
    return selectedCategory === category;
  };

  const handleLogoClick = () => {
    onSelectCategory(null);

    if (onCategoryClick) {
      onCategoryClick();
    }
  };

  return (
    <Box marginLeft="5px" marginRight="5px">
      {categories.map((category) => (
        <Button
          key={category}
          variant={isCategorySelected(category) ? 'solid' : 'outline'}
          colorScheme="blue"
          onClick={() => handleCategorySelect(category)}
          fill={true}
          width="100%"
          marginBottom="10px"
        >
          {category}
        </Button>
      ))}
      <Button
        variant={selectedCategory === null ? 'solid' : 'outline'}
        colorScheme="blue"
        onClick={handleLogoClick}
        fill={true}
        width="100%"
        marginBottom="10px"
      >
        모두 보기
      </Button>
    </Box>
  );
};

export default CategorySelect;