package org.JKDW.user.controller;


import java.util.List;

import org.JKDW.user.model.DTO.UserAccountCreateDTO;
import org.JKDW.user.model.DTO.UserAccountDTO;
import org.JKDW.user.model.UserAccount;
import org.JKDW.user.model.UserDetails;
import org.JKDW.user.service.UserAccountService;
import org.JKDW.user.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private UserDetailsService userDetailsService;


	/**
	 *
	 * @return all user accounts
	 */
	@RequestMapping( value="/all",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserAccount>> getUserAccounts(){
		List<UserAccount> userAccounts = userAccountService.getAllUserAccounts();
		return new ResponseEntity<>(userAccounts,HttpStatus.OK);
	}

	/**
	 *
	 * @param name - username of user account
	 * @return	user specified by name
	 */
	@RequestMapping(value="/account/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAccount> getUserAccountByName(@PathVariable("name") String name ){
		UserAccount userAccount = userAccountService.loadUserByUsername(name);
		return new ResponseEntity<>(userAccount,HttpStatus.OK);
	}

	/**
	 *
	 * @param userAccount new user account info
	 * @return new user
	 */
	@RequestMapping(value="/create",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccountCreateDTO userAccount) {
		UserAccount createdUserAccount = userAccountService.createUserAccount(userAccount);
		//create details and link it with userAccount
		UserDetails userDetails = new UserDetails();
		userDetails.setUserAccount(createdUserAccount);
		userDetailsService.createUserDetails(userDetails);
		return new ResponseEntity<>(createdUserAccount,HttpStatus.CREATED);
	}

	/**
	 *
	 * @param userAccount - data to update
	 * @return updated account information
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAccount> updateUserAccount(@RequestBody UserAccount userAccount){
		UserAccount updatedUserAccount = userAccountService.updateUserAccount(userAccount);
		return new ResponseEntity<>(updatedUserAccount,HttpStatus.OK);
	}

	/**
	 *
	 *
	 * @param id - id of deleting account
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity deleteUserAccount(@PathVariable("id") Long id){
		userAccountService.deleteUserAccount(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/account/test/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAccountDTO> getUserAccountByName(@PathVariable("id") Long id) {
		UserAccountDTO userAccountDTO = userAccountService.getUserAccountDTOById(id);
		return new ResponseEntity<>(userAccountDTO, HttpStatus.OK);
	}

}
