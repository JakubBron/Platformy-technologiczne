using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using MessageBox = System.Windows.MessageBox;

namespace Lab10
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private static readonly List<Car> MyCars = new List<Car>
        {
            new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
            new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
            new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
            new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
            new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
            new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
            new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
            new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
            new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
        };
        private List<Car> _tempCars;
        private BindingList<Car> _myCarsBindingList;
        private SearchableAndSortableBindingList _carList = new SearchableAndSortableBindingList(MyCars);

        public MainWindow()
        {
            InitializeComponent();

            ComboBox.Items.Add("Model");
            ComboBox.Items.Add("Motor");
            ComboBox.Items.Add("Year");

            BindDataToGrid(MyCars);

            //query_expression();
            //method_based();
            //Task02();
        }

        private void BindDataToGrid(List<Car> cars)
        {
            _myCarsBindingList = new BindingList<Car>(cars);
            //var carBindingSource = new BindingSource();
            //carBindingSource.DataSource = _myCarsBindingList;
            //CarsDataGrid.ItemsSource = carBindingSource;
            CarsDataGrid.ItemsSource = _myCarsBindingList;
        }

        private static void Task02()
        {
            Func<Car, Car, int> arg1 = Comparator;
            Predicate<Car> arg2 = Predicate;
            Action<Car> arg3 = Action;
            MyCars.Sort(new Comparison<Car>(arg1));
            MyCars.FindAll(arg2).ForEach(arg3);
        }

        private static int Comparator(Car car, Car b)
        {
            if (car.motor.horsePower > b.motor.horsePower)
            {
                return 1;
            }

            if (car.motor.horsePower < b.motor.horsePower)
            {
                return -1;
            }

            return 0;
        }

        private static bool Predicate(Car a)
        {
            return a.motor.model == "TDI";
        }

        private static void Action(Car a)
        {
            MessageBox.Show("2. model: " + a.model + " Silnik: " + a.motor + " Rok: " + a.year);
        }

        public void HandleKeyPress(object sender, System.Windows.Input.KeyEventArgs e)
        {
        }

        private void Search_Button(object sender, RoutedEventArgs e)
        {
            var query = SearchTextBox.Text;
            var property = ComboBox.SelectedItem.ToString();
            _tempCars = _carList.Find(query, property);
            BindDataToGrid(_tempCars);
        }

        public void Add_Button(object sender, RoutedEventArgs e)
        {   // names same sa in XAML
            var _model = Model.Text;
            var _enginemodel = EngineModel.Text;
            var _horsepower = float.Parse(Horsepower.Text); ;
            var _displacement = float.Parse(Displacement.Text);
            var _year = int.Parse(Year.Text);



            _tempCars = _carList.AddElement(_model, _enginemodel, _horsepower, _displacement, _year);
            _carList = new SearchableAndSortableBindingList(_tempCars);
            BindDataToGrid(_tempCars);
        }

        public void Remove_Button(object sender, RoutedEventArgs e)
        {
            // names same sa in XAML
            var _model = Model.Text;
            var _enginemodel = EngineModel.Text;
            var _horsepower = float.Parse(Horsepower.Text); ;
            var _displacement = float.Parse(Displacement.Text);
            var _year = int.Parse(Year.Text);

            _tempCars = _carList.RemoveElement(_model, _enginemodel, _horsepower, _displacement, _year);
            _carList = new SearchableAndSortableBindingList(_tempCars);
            BindDataToGrid(_tempCars);
        }

        public void Update_Button(object sender, RoutedEventArgs e)
        {
            // names same sa in XAML
            var _model = Model.Text;
            var _enginemodel = EngineModel.Text;
            var _horsepower = float.Parse(Horsepower.Text); ;
            var _displacement = float.Parse(Displacement.Text);
            var _year = int.Parse(Year.Text);

            var _newmodel = NewModel.Text;
            var _newenginemodel = NewEngineModel.Text;
            var _newhorsepower = float.Parse(NewHorsepower.Text); ;
            var _newdisplacement = float.Parse(NewDisplacement.Text);
            var _newyear = int.Parse(NewYear.Text);

            _tempCars = _carList.RemoveElement(_model, _enginemodel, _horsepower, _displacement, _year);
            _carList = new SearchableAndSortableBindingList(_tempCars);

            _tempCars = _carList.AddElement(_newmodel, _newenginemodel, _newhorsepower, _newdisplacement, _newyear);
            _carList = new SearchableAndSortableBindingList(_tempCars);

            BindDataToGrid(_tempCars);
        }


        private static void query_expression()
        {
            var result = from c in MyCars
                         where c.model == "A6"
                         let engineType = c.motor.model == "TDI" ? "diesel" : "petrol"
                         let hppl = (double)c.motor.horsePower / c.motor.displacement
                         group hppl by engineType
                into g
                         orderby g.Average() descending
                         select new
                         {
                             engineType = g.Key,
                             avgHPPL = g.Average()
                         };

            var odp = result.Aggregate("query_expression \n", (current, e) => current + (e.engineType + ": " + e.avgHPPL + " \n"));
            MessageBox.Show(odp);
        }

        private static void method_based()
        {
            var result = MyCars
                .Where(c => c.model == "A6")
                .Select(c => new
                {
                    engineType = c.motor.model == "TDI" ? "diesel" : "petrol",
                    hppl = (double)c.motor.horsePower / c.motor.displacement
                })
                .GroupBy(c => c.engineType)
                .Select(g => new
                {
                    engineType = g.Key,
                    avgHPPL = g.Average(c => c.hppl)
                })
                .OrderByDescending(c => c.avgHPPL);

            var odp = result.Aggregate("method-based query \n", (current, e) => current + (e.engineType + ": " + e.avgHPPL + " \n"));
            MessageBox.Show(odp);
        }

        private void Sort_Model(object sender, RoutedEventArgs e)
        {
            _tempCars = _carList.Sort("model");
            BindDataToGrid(_tempCars);
        }

        private void Sort_Year(object sender, RoutedEventArgs e)
        {
            _tempCars = _carList.Sort("year");
            BindDataToGrid(_tempCars);
        }

        private void Sort_Motor(object sender, RoutedEventArgs e)
        {
            _tempCars = _carList.Sort("motor");
            BindDataToGrid(_tempCars);
        }

    }
}
