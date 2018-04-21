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
    
    var openingHours = [String:Date?]()
    
    var closingHours = [String:Date?]()
    
    class ConstansIdentifiers{
        static let NewPlace:String = "Editable Cell"
        static let ExistingPlace:String = "Simple Cell"
        static let Date:String = "Date Picker"
        static let ToiletIsReady:String = "Add Toilet"
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
                self.openingHours[name] = date
                print(self.openingHours)
            }
            element.closingSetted = { date,name in
                self.closingHours[name] = date
                print(self.closingHours)
            }
            element.dayRemoved = { name in
                self.openingHours.removeValue(forKey: name)
                self.closingHours.removeValue(forKey: name)
                print(self.openingHours)
                print(self.closingHours)
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == ConstansIdentifiers.ToiletIsReady {
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
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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

    /*
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)

        // Configure the cell...

        return cell
    }
    */

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
