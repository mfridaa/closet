//
//  NewToiletTableViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 21..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import CoreData
import MapKit

class NewToiletTableViewController: UITableViewController,UITextFieldDelegate {
    var container:NSPersistentContainer? = (UIApplication.shared.delegate as! AppDelegate).persistentContainer
    
    @IBOutlet var openinInformationsCells: [DatePickerTableViewCell]!
    
    @IBOutlet weak var nameCell: NewValueTableViewCell!
    
    @IBOutlet weak var nameLabel: UITextField!
    
    var newName:String?
    
    var coordinates:CLLocationCoordinate2D?
    
    var openingHours = [String:String]()
    
    var closingHours = [String:String]()
    
    class ConstansIdentifiers{
        static let NewPlace:String = "Editable Cell"
        static let ExistingPlace:String = "Simple Cell"
        static let Date:String = "Date Picker"
        static let ToiletIsReady:String = "Add Toilet"
    }
    
    var databaseChanged:(()->Void)?
    
    private func saveToiletToServer(){
        if let url = URL(string : URLStorage.addToilet)  {
            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")
            let newToilet = BasicToilet(id: 0, name: newName ?? "Unnamed", location: MapCoordinate(latitude: Float((coordinates?.latitude)!), longitude: Float((coordinates?.longitude)!)), rating: 0, status: "unknown")
            var allOpeningHours = [OpeningHour]()
            for day in openingHours.keys{
                if let closingHour = closingHours[day]{
                    if let openingHour = openingHours[day]{
                        allOpeningHours.append(OpeningHour(day: day, openingHour: openingHour, closingHour: closingHour))
                    }
                    
                }
            }
            let postToilet = [newToilet.PostFormWith(openingHours: allOpeningHours)]
            do{
                let jsonBody = try JSONEncoder().encode(postToilet)
                request.httpBody = jsonBody
            } catch {}
            
            URLSession.shared.dataTask(with: request) { data, response, err in
                if let data = data {
                    do{
                        let newToilet = try JSONDecoder().decode([BasicToilet].self, from: data)
                        
                            if let context = self.container?.viewContext{
                                context.perform {
                                    _ = try? Toilet.findOrCreateToilet(matching: newToilet.first!, in: context)
                                    do{
                                        try context.save()
                                        
                                    }catch{
                                        print("error")
                                    }
                                    DispatchQueue.main.async {
                                        if let function = self.databaseChanged{
                                            function()
                                        }
                                    }
                                }
                               
                                
                               
                                
                            }
                      

                    }catch let jsonErr{
                        print("Error serializing json:" ,jsonErr)
                    }
                }
                else{
                    print("error")
                }
                }.resume()
        }
    }

    
    private func saveToiletToDatebase(toilet:BasicToilet){
        if let context = container?.viewContext{
            let toilet = Toilet(context: context)
            toilet.name = newName ?? "No name"
            toilet.id = 100
            toilet.latitude = Float((coordinates?.latitude)!)
            toilet.longitude = Float((coordinates?.longitude)!)
            do{
                try context.save()
                
            }catch{
                print("error")
            }
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        nameCell.cellName.text = "Name"
        nameLabel.delegate = self
        nameCell.changeHappened = { value in
            self.newName = value
        }
        for (index, element) in openinInformationsCells.enumerated(){
            element.dayOfWeek.text = Day.getDayFor(day: index)
            element.openingSetted = { date,name in
                self.openingHours[name] = self.dateToRightStringFormat(from: date)
            }
            element.closingSetted = { date,name in
                self.closingHours[name] = self.dateToRightStringFormat(from: date)
            }
            element.dayRemoved = { name in
                self.openingHours.removeValue(forKey: name)
                self.closingHours.removeValue(forKey: name)
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == ConstansIdentifiers.ToiletIsReady {
            saveToiletToServer()
           
            
        }
    }
    
    private func dateToRightStringFormat(from date:Date) -> String{
        let calendar = Calendar.current
        
        let hour = calendar.component(.hour, from: date)
        let minutes = calendar.component(.minute, from: date)
      
        let hourString = hour == 0 ? "00" : hour < 10 ? "0\(hour)" : String.init(hour)
        let minuteString = minutes == 0 ? "00" : minutes < 10 ? "0\(minutes)" : String.init(minutes)
        return "\(hourString):\(minuteString)"
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 2
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return section == 0 ? 1 : 7
    }



}
