-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 04, 2018 at 09:55 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mobiledb`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `categoryid` int(11) NOT NULL,
  `categoryname` varchar(255) NOT NULL,
  `totalproducts` int(20) NOT NULL,
  `tvalueproducts` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`categoryid`, `categoryname`, `totalproducts`, `tvalueproducts`) VALUES
(1, 'phone', 3, 40000),
(2, 'Charger', 23, 6100),
(8, 'samsung', 0, 0),
(9, 'earphone', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerid` int(11) NOT NULL,
  `customername` varchar(255) NOT NULL,
  `phoneno` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerid`, `customername`, `phoneno`, `email`) VALUES
(2, 'Dummy', '9876543210', 'dummy@gmail.com'),
(3, 'Dummy1', '9876543212', 'dum@gmail.com'),
(4, 'Vinit', '9638527410', 'vinit@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `invoiceno` int(11) NOT NULL,
  `customeremail` varchar(255) NOT NULL,
  `GST` int(11) NOT NULL,
  `totalprice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`invoiceno`, `customeremail`, `GST`, `totalprice`) VALUES
(1, 'rohra470@gmail.com', 8000, 58000),
(2, 'rohra470@gmail.com', 1632, 11832),
(3, 'dummy@gmail.com', 1632, 11832),
(4, 'dummy@gmail.com', 3200, 23200),
(5, 'dum@gmail.com', 16, 116),
(6, 'default@gmail.com', 960, 6960),
(7, 'default@gmail.com', 960, 6960),
(8, 'default@gmail.com', 960, 6960),
(9, 'dummy@gmail.com', 3200, 23200),
(10, 'sunny@gmail.com', 16000, 116000),
(11, 'dum@gmail.com', 3200, 23200),
(12, 'dummy@gmail.com', 1600, 11600),
(13, 'dummy@gmail.com', 3200, 23200),
(14, 'dummy@gmail.com', 1600, 11600),
(15, 'dummy@gmail.com', 1600, 11600),
(16, 'dummy@gmail.com', 3200, 23200),
(17, 'vinit@gmail.com', 1600, 11600),
(18, 'vishal.jeshnani@gmail.com', 3200, 23200),
(19, 'dum@gmail.com', 3200, 23200);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `categoryid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `productmodel` varchar(255) NOT NULL,
  `brandname` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`categoryid`, `productid`, `productmodel`, `brandname`, `price`, `quantity`) VALUES
(1, 2, 'Note4', 'Redmi', 10000, 2),
(2, 3, 'A2', 'Samsung', 200, 8),
(1, 6, 'A7', 'Samsung', 20000, 1),
(2, 8, 'A8', 'Sony', 300, 15);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`categoryid`),
  ADD KEY `categoryid` (`categoryid`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerid`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`invoiceno`),
  ADD UNIQUE KEY `invoiceno` (`invoiceno`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productid`),
  ADD KEY `productid` (`productid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `categoryid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customerid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `invoiceno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `productid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
