export interface MenuItem {
  name: string;
  price: number;
  description?: string;
  imageUrl?: string;
}

export interface Restaurant {
  id: string;
  name: string;
  imageUrls: string[];
  averagePrice: number;
  location: string;
  categories: string[];
  menu: MenuItem[];
  bookmarked: boolean;
}
